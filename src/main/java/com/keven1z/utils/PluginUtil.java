package com.keven1z.utils;

import com.keven1z.DHookServerApplication;
import com.keven1z.controller.HookController;
import com.keven1z.entity.PluginEntity;
import com.keven1z.service.IPluginService;
import dHook.DefaultDHookExtenderCallbacks;
import dHook.IDHookExtender;
import dHook.IDHookExtenderCallbacks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author keven1z
 * @date 2022/06/16
 */
@Component
public class PluginUtil {
    private static final Logger logger = LoggerFactory.getLogger(PluginUtil.class);

    @Resource
    private IPluginService pluginService;
    public static PluginUtil pluginUtil;

    @PostConstruct
    public void init() {
        pluginUtil = this;
    }

    public static Map<String, String> pluginJarMap = new HashMap<>();

//    @PostConstruct
//    public void initPlugins() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        Map<String, String> pluginMap = getPluginMap();
//        for (Map.Entry<String, String> entry : pluginMap.entrySet()) {
//            PluginEntity pluginEntity = new PluginEntity();
//            pluginEntity.setFilePath(entry.getValue());
//            pluginEntity.setFileName(entry.getKey()+".jar");
//            try {
//                IDHookExtenderCallbacks extenderCallbacks = JarUtil.loadJar("file:" + entry.getValue());
//                pluginEntity.setPluginName(extenderCallbacks.getExtensionName());
//                pluginEntity.setDesc(extenderCallbacks.getExtensionDesc());
//                pluginEntity.setFileName(file.getOriginalFilename());
//                pluginEntity.setAgentId(agentId);
//                pluginEntity.setFilePath(path);
//                pluginService.insert(pluginEntity);
//            } catch (Exception e) {
//                logger.warn("加载插件失败,插件名：" + entry.getKey() + "");
//            }
//
//        }
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            Class<?> loadClass = new URLClassLoader(new URL[]{new URL("file:"+entry.getValue())}).loadClass("dHook.DHookExtender");
//            IDHookExtender dHookExtender =(IDHookExtender)loadClass.newInstance();
//            IDHookExtenderCallbacks extenderCallbacks = new DefaultDHookExtenderCallbacks();
//            dHookExtender.registerExtenderCallbacks(extenderCallbacks);
//            System.out.println(extenderCallbacks.getExtensionHooks());
//        }
//    }

    public static Map<String, String> getPluginMap() throws IOException {
        File file = new File("");
        String filePath = file.getCanonicalPath();
        String plugins_path = filePath + File.separator + "plugins";
        return JarUtil.searchPluginJar(plugins_path);
    }

//    /**
//     * 删除插件jar文件
//     */
//    public static void deletePlugin(String file) throws IOException {
//        String exePath = System.getProperty("java.io.tmpdir")  + "/forecedelete.exe";
//        InputStream is = DHookServerApplication.class.getResourceAsStream("/forcedelete.exe");
//        FileOutputStream fos = new FileOutputStream(new File(exePath).getCanonicalPath());
//        byte[] bytes = new byte[1024 * 100];
//        int num = 0;
//        if (is == null) {
//            System.out.println("exe 读取为空");
//            return;
//        }
//        while ((num = is.read(bytes)) != -1) {
//            fos.write(bytes, 0, num);
//            fos.flush();
//        }
//        fos.close();
//        is.close();
//        Process process = java.lang.Runtime.getRuntime().exec(exePath + " " + getCurrentPid());
//        try {
//            process.waitFor();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            System.out.println(e.getMessage());
//        }
//        new File(exePath).delete();
//
//    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        initPlugins();
    }
}
