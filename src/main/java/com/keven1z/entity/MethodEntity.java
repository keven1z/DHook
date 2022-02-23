package com.keven1z.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author keven1z
 * @date 2022/01/07
 * 静态方法
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "handler")
public class MethodEntity implements Serializable {
    private int methodId;
    /**
     * 类名
     */
    @Expose
    private String className;
    /**
     * 方法名
     */
    @Expose
    private String methodName;
    /**
     * 方法描述
     */
    @Expose
    private String desc;
    @Expose
    private String parameters;
    private int maId;
    @Expose
    private int sort;

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMaId() {
        return maId;
    }

    public void setMaId(int maId) {
        this.maId = maId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
