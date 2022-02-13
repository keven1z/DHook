package com.keven1z.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author keven1z
 * @date 2021/12/27
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "handler")
public class HookEntity implements Serializable {
    private int id;
    @Expose
    private String className;
    @Expose
    private String method;
    @Expose
    private String desc;
    @Expose
    private String parameter;
    @Expose
    private Object returnValue;
    private String agentId;
    @Expose
    private List<MethodActionEntity> onMethodAction;

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public List<MethodActionEntity> getOnMethodAction() {
        return onMethodAction;
    }

    public void setOnMethodAction(List<MethodActionEntity> onMethodAction) {
        this.onMethodAction = onMethodAction;
    }
}
