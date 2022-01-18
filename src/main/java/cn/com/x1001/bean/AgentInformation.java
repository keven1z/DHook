package cn.com.x1001.bean;

import cn.com.x1001.hook.HookConsts;
import cn.com.x1001.util.OSUtil;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author keven1z
 * @date 2021/12/22
 */
public class AgentInformation {
    private String id;
    private int state;
    private String time;
    private String javaVersion;
    private String os;
    private String bindProcessName;

    public AgentInformation() throws Exception {
        this.id = HookConsts.REGISTER_ID;
        this.os = OSUtil.getOs();
        this.state = 1;
        this.bindProcessName = ManagementFactory.getRuntimeMXBean().getName();
        this.javaVersion = System.getProperty("java.version");
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");
        try {
            this.time = dateFormat.format(date);
        } catch (Exception e) {
        }
    }

    public String getAgentId() {
        return id;
    }

    @Override
    public String toString() {
        return "AgentInformation{" +
                "id='" + id + '\'' +
                ", bindProcessName='" + bindProcessName + '\'' +
                ", state=" + state +
                ", time='" + time + '\'' +
                ", javaVersion='" + javaVersion + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
