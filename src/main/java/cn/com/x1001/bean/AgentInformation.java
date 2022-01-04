package cn.com.x1001.bean;

import cn.com.x1001.util.OSUtil;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author keven1z
 * @date 2021/12/22
 */
public class AgentInformation {
    private String id;
    private String name;
    private int state;
    private String time;
    private String javaVersion;
    private String os;

    public AgentInformation() throws Exception {
        this.id = OSUtil.getAgentId();
        this.os = OSUtil.getOs();
        this.state = 1;
        this.name = ManagementFactory.getRuntimeMXBean().getName();
        this.javaVersion = System.getProperty("java.version");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.time = sdf.format(timestamp);
    }

    public String getAgentId() {
        return id;
    }

    @Override
    public String toString() {
        return "AgentInformation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", time='" + time + '\'' +
                ", javaVersion='" + javaVersion + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
