package com.keven1z.entity;

/**
 * @author keven1z
 * @date 2022/04/20
 */
public class PluginEntity {
    private String pluginName;
    private String fileName;
    private String desc;

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
