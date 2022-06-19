package com.keven1z.utils;

import com.keven1z.service.IPluginService;
import dHook.DefaultDHookExtenderCallbacks;
import dHook.IDHookExtender;
import dHook.IDHookExtenderCallbacks;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author keven1z
 * @date 2022/06/16
 */
public class PluginUtil {
    @Resource
    private IPluginService pluginService;
    public static PluginUtil pluginUtil;

    @PostConstruct
    public void init() {
        pluginUtil = this;
    }

    public static Map<String, String> pluginJarMap = new HashMap<>();

    public static void initPlugins() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        File file = new File("");
        String filePath = file.getCanonicalPath();
        String plugins_path = filePath + File.separator + "plugins";

        Map<String, String> map = JarUtil.searchPluginJar(plugins_path);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Class<?> loadClass = new URLClassLoader(new URL[]{new URL("file:"+entry.getValue())}).loadClass("dHook.DHookExtender");
            IDHookExtender dHookExtender =(IDHookExtender)loadClass.newInstance();
            IDHookExtenderCallbacks extenderCallbacks = new DefaultDHookExtenderCallbacks();
            dHookExtender.registerExtenderCallbacks(extenderCallbacks);
            System.out.println(extenderCallbacks.getExtensionHooks());
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        initPlugins();
    }
}
