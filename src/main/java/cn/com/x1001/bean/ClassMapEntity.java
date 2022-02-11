package cn.com.x1001.bean;

import cn.com.x1001.hook.HookConsts;

/**
 * @author keven1z
 * @date 2022/01/05
 */

public class ClassMapEntity {
    private String className;
    private String packageName;
    private String agentId;

    public ClassMapEntity() {
    }

    public ClassMapEntity(String className, String packageName) {
        this.className = className;
        this.packageName = packageName;
        this.agentId = HookConsts.REGISTER_ID;
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
