package com.keven1z.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Getter
@Setter
@ToString
public class AgentEntity {
    private String id;

    /**
     * 应用名称
     */
    private String name;
    /**
     * 连接状态
     */
    private int state;
    /**
     * 上一次连接时间
     */
    private String time;
    /**
     * 应用jdk版本
     */
    private String javaVersion;
    /**
     * 应用操作系统信息
     */
    private String os;
    /**
     * agent绑定的应用的进程的信息
     */
    private String bindProcessName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJavaVersion() {
        return javaVersion;
    }

    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBindProcessName() {
        return bindProcessName;
    }

    public void setBindProcessName(String bindProcessName) {
        this.bindProcessName = bindProcessName;
    }
}
