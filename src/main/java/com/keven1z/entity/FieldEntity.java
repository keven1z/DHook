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
    private int type;
    @Expose
    private String name;
    @Expose
    private Object value;
    private int maId;
    @Expose
    private int sort;

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getMaId() {
        return maId;
    }

    public void setMaId(int maId) {
        this.maId = maId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
