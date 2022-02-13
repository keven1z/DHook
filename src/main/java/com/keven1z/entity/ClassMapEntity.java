package com.keven1z.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author keven1z
 * @date 2022/01/05
 */
@Getter
@Setter
@ToString
public class ClassMapEntity {
    private int id;
    private String className;
    private String packageName;
    private String agentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
