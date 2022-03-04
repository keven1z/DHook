package com.keven1z.utils;


import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import java.io.*;
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
//        String outJarPath = System.getProperty("java.io.tmpdir") + File.separator +"dHook.jar";
        return JarLoader.saveToJar(agent, changeNodes);
    }

    public static byte[] updateConfig(String value) throws IOException {
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
            if (entryName.equals("cn/com/x1001/config.properties")){
                jarEntry = new JarEntry(entryName);
                jos.putNextEntry(jarEntry);
                Properties properties = new Properties();
                properties.setProperty("register_id", value);
                properties.load(in);
                properties.store(jos, null);
            }
            else {
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
            if (entryName.equals("cn/com/x1001/hook.json")){
                jarEntry = new JarEntry(entryName);
                jos.putNextEntry(jarEntry);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(jos));
                bufferedWriter.write(json);
                bufferedWriter.flush();
            }
            else {
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


    public static void main(String[] args) throws Exception {
//        byte[] bytes = JarUtil.updateField("cn/com/x1001/Config", "registerID", "registerID");
//        byte[] bytes = updateConfig("aaaa");
//        System.out.println(bytes.length);
//        int c= 999;
//        updateHook("[{\"className\":\"common/Authorization\",\"method\":\"\\u003cinit\\u003e\",\"desc\":\"()V\",\"returnValue\":\"\",\"onMethodAction\":[{\"type\":1,\"fields\":[{\"name\":\"validto\",\"value\":\"forever\",\"sort\":0},{\"name\":\"valid\",\"value\":\"true\",\"sort\":1}],\"methods\":[{\"className\":\"common/SleevedResource\",\"methodName\":\"Setup\",\"desc\":\"([B)V\",\"parameters\":\"94,-104,25,74,1,-58,-76,-113,-91,-126,-90,-87,-4,-69,-110,-42\",\"sort\":0},{\"className\":\"return\",\"methodName\":\"\",\"desc\":\"\",\"parameters\":\"\",\"sort\":0}]},{\"type\":2,\"fields\":[],\"methods\":[]}]}]");
    }
}
