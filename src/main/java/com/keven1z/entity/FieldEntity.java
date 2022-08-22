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
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "handler")
public class FieldEntity{

    private int fieldId;
    /**
     * 参数名
     */
    @Expose
    private String name;
    /**
     * 参数值
     */
    @Expose
    private Object value;
    /**
     * 对应的hook id
     */
    private int hookId;
    /**
     * 执行顺序
     */
    @Expose
    private int sort;
    /**
     * 该参数修改是在方法执行前还是执行后
     */
    @Expose
    private int type;
}
