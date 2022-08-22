package com.keven1z.utils;

import com.keven1z.entity.ConfigEntity;
import com.keven1z.entity.PluginEntity;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * @author keven1z
 * @date 2022/01/17
 */
public class JarUtil {
    /**
     * 修改agent中的@className的@fieldName
     *
     * @param className  待更新field的类名
     * @param fieldName  待更新field的name
     * @param fieldValue 待更新field的value
     * @return 修改后agent的路径
     */
    public static byte[] updateField(String className, String fieldName, String fieldValue) throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("agent/dHook.jar");
        Resource resource = resources[0];
        File file = resource.getFile();
        InputStream inputStream = resource.getInputStream();
        String tmpAgent = System.getProperty("java.io.tmpdir") + File.separator + "dHook.jar";
        File agent = new File(tmpAgent);
        CommonUtils.copy(inputStream, agent);

        List<ClassNode> classNodes = JarLoader.loadJar(agent);

        List<ClassNode> changeNodes = new ArrayList<>();
        for (ClassNode classNode : classNodes) {
            if (!className.equals(classNode.name)) {
                changeNodes.add(classNode);
                continue;
            }
            for (FieldNode field : classNode.fields) {
                if (fieldName.equals(field.name)) {
                    field.value = fieldValue;
                }
            }
            changeNodes.add(classNode);
        }
//        String inJarPath = file.getAbsolutePath();
//        String outJarPath = System.getProperty("java.io.tmpdir") + File.separator +"dHook.jar1";
        return JarLoader.saveToJar(agent, changeNodes);
    }

    public static byte[] updateConfig(String id,List<ConfigEntity> configEntities) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("agent/dHook.jar");
        Resource resource = resources[0];
        String tmpAgent = System.getProperty("java.io.tmpdir") + File.separator + "dHook.jar";
        File agent = new File(tmpAgent);
        CommonUtils.copy(resource.getInputStream(), agent);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

//            CommonUtils.copy(jarFile, tmpJarFile);
        JarFile jf = new JarFile(agent);
        JarOutputStream jos = new JarOutputStream(byteArrayOutputStream);
        Enumeration<JarEntry> entries = jf.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String entryName = jarEntry.getName();
            InputStream in = jf.getInputStream(jarEntry);
            if (entryName.equals("com/keven1z/config.properties")) {
                jarEntry = new JarEntry(entryName);
                jos.putNextEntry(jarEntry);
                Properties properties = new Properties();
                properties.setProperty("register_id", id);
                for (ConfigEntity configEntity:configEntities){
                    properties.setProperty(configEntity.getName(), configEntity.getValue());
                }
                properties.load(in);
                properties.store(jos, null);
            } else {
                jos.putNextEntry(jarEntry);
                CommonUtils.inputStreamToOutputStream(in, jos);
            }
            jos.closeEntry();
            in.close();
        }
        jos.close();
        jf.close();
        byteArrayOutputStream.writeTo(new FileOutputStream(agent));
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] updateHook(String json) throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("agent/dHook-offline.jar");
        Resource resource = resources[0];
        String tmpAgent = System.getProperty("java.io.tmpdir") + File.separator + "dHook-offline.jar";
        System.out.println(tmpAgent);
        File agent = new File(tmpAgent);
        CommonUtils.copy(resource.getInputStream(), agent);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

//            CommonUtils.copy(jarFile, tmpJarFile);
        JarFile jf = new JarFile(agent);
        JarOutputStream jos = new JarOutputStream(byteArrayOutputStream);
        Enumeration<JarEntry> entries = jf.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String entryName = jarEntry.getName();
            InputStream in = jf.getInputStream(jarEntry);
            if (entryName.equals("cn/com/x1001/hook.json")) {
                jarEntry = new JarEntry(entryName);
                jos.putNextEntry(jarEntry);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(jos));
                bufferedWriter.write(json);
                bufferedWriter.flush();
            } else {
                jos.putNextEntry(jarEntry);
                CommonUtils.inputStreamToOutputStream(in, jos);
            }
            jos.closeEntry();
            in.close();
        }
        jos.close();
        jf.close();
        byteArrayOutputStream.writeTo(new FileOutputStream(agent));
        return byteArrayOutputStream.toByteArray();
    }

    public static Map<String, String> searchPluginJar(String dir_path) {
        HashMap<String, String> plugins = new HashMap<>();
        File dir = new File(dir_path);
        if (!dir.isDirectory()) return plugins;

        String[] children = dir.list();
        for (int i = 0; i < children.length; i++) {
            String plugin = children[i];
            if (plugin.endsWith(".jar")) {
                String plugin_path = dir_path + File.separator + plugin;
                plugins.put(plugin, plugin_path);
            }
        }
        return plugins;
    }

    public static PluginEntity loadJar(String path) throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        URLClassLoader urlClassLoader = null;
        Object defaultDHookExtenderCallbacks;
        PluginEntity pluginEntity = new PluginEntity();
        try {
            urlClassLoader = new URLClassLoader(new URL[]{new URL(path)});
            Class<?> defaultDHookExtenderCallbacksClass = urlClassLoader.loadClass("dHook.DefaultDHookExtenderCallbacks");
            defaultDHookExtenderCallbacks = defaultDHookExtenderCallbacksClass.getDeclaredConstructor(new Class[]{}).newInstance();
            Class<?> loadClass = urlClassLoader.loadClass("dHook.DHookExtender");

            Method registerExtenderCallbacks = loadClass.getDeclaredMethod("registerExtenderCallbacks", urlClassLoader.loadClass("dHook.IDHookExtenderCallbacks"));
            Object dHookExtender = loadClass.getDeclaredConstructor(new Class[]{}).newInstance();
            registerExtenderCallbacks.invoke(dHookExtender, defaultDHookExtenderCallbacks);

            Method getExtensionNameMethod = defaultDHookExtenderCallbacksClass.getMethod("getExtensionName");
            Method getExtensionDescMethod = defaultDHookExtenderCallbacksClass.getMethod("getExtensionDesc");
            String extensionName = getExtensionNameMethod.invoke(defaultDHookExtenderCallbacks).toString();
            String extensionDesc = getExtensionDescMethod.invoke(defaultDHookExtenderCallbacks).toString();

            pluginEntity.setPluginName(extensionName);
            pluginEntity.setDesc(extensionDesc);
        } finally {
            if (urlClassLoader != null) urlClassLoader.close();
        }
        return pluginEntity;
    }
}
