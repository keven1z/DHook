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
    /**
     * hook的类名，包含包路径，eg:org/apache/catalina/connector/Request
     */
    @Expose
    private String className;
    /**
     * hook的方法名
     */
    @Expose
    private String method;
    /**
     * hook的方法描述,eg:(Ljava/lang/String;)Ljava/lang/String;
     */
    @Expose
    private String desc;
    /**
     * 修改的入参，暂未实现
     */
    @Expose
    private String parameters;
    /**
     * 修改返回值
     */
    @Expose
    private Object returnValue;
    private String agentId;
    /**
     * 修改方法执行时的属性
     */
    @Expose
    private List<FieldEntity> fieldEntities;
    /**
     * 增加方法执行时的方法
     */
    @Expose
    private List<MethodEntity> methodEntities;

}
