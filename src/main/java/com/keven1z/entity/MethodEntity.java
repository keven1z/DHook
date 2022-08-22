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
    /**
     * 修改的参数
     */
    @Expose
    private String parameters;
    private int hookId = -1;
    @Expose
    private int sort;
    @Expose
    private int type;


}
