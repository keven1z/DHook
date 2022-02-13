package com.keven1z.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Getter
@Setter
@ToString
public class AgentEntity {
    private String id;
    private String name;
    private int state;
    private String time;
    private String javaVersion;
    private String os;
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

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
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

    public String getBindProcessName() {
        return bindProcessName;
    }

    public void setBindProcessName(String bindProcessName) {
        this.bindProcessName = bindProcessName;
    }
}
