package com.keven1z.entity;

/**
 * @author keven1z
 * @date 2022/04/20
 */
public class PluginEntity {
    /**
     * 文件名，主键
     */
    private String fileName;
    /**
     * 插件名称
     */
    private String pluginName;
    /**
     * 插件绝对路径
     */
    private String filePath;
    /**
     * 插件描述
     */
    private String desc;
    /**
     * 对应的agent
     */
    private String agentId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
