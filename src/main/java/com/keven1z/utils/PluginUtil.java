package com.keven1z.utils;


import com.keven1z.service.IPluginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

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

//    public static Map<String, String> getPluginMap() throws IOException {
//        File file = new File("");
//        String filePath = file.getCanonicalPath();
//        String plugins_path = filePath + File.separator + "plugins";
//        return JarUtil.searchPluginJar(plugins_path);
//    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        initPlugins();
    }
}
