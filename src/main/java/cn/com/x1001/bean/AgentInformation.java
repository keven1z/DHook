package cn.com.x1001.bean;

import cn.com.x1001.util.MacUtil;
import cn.com.x1001.util.UUidUtil;

/**
 * @author keven1z
 * @date 2021/12/22
 */
public class AgentInformation {
    private String agentId;
    private String os;
    private String javaVersion;
    private String mac;
    private long time;

    public AgentInformation(){
//        this.agentId = UUidUtil.getUUID();
        this.os = System.getProperty("os.name");
        this.javaVersion = System.getProperty("java.version");
        this.mac = MacUtil.getMacAddress();
        this.time = System.currentTimeMillis();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "AgentInformation{" +
                "agentId='" + agentId + '\'' +
                ", os='" + os + '\'' +
                ", javaVersion='" + javaVersion + '\'' +
                ", mac='" + mac + '\'' +
                ", time=" + time +
                '}';
    }
}
