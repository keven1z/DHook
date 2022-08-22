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
}
