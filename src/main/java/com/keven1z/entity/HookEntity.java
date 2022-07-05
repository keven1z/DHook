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
    private String parameters;
    @Expose
    private Object returnValue;
    private String agentId;
    @Expose
    private List<FieldEntity> fieldEntities;
    @Expose
    private List<MethodEntity> methodEntities;

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

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
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

    public List<FieldEntity> getFieldEntities() {
        return fieldEntities;
    }

    public void setFieldEntities(List<FieldEntity> fieldEntities) {
        this.fieldEntities = fieldEntities;
    }

    public List<MethodEntity> getMethodEntities() {
        return methodEntities;
    }

    public void setMethodEntities(List<MethodEntity> methodEntities) {
        this.methodEntities = methodEntities;
    }
}
