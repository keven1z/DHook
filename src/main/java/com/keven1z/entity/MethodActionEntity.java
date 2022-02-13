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
 * @date 2022/01/07
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = "handler")
public class MethodActionEntity implements Serializable {
//    /*
//     * 1: onMethodEnter
//     * 2: onMethodExit
//     */
    private int maId;
    @Expose
    private int type;
    @Expose
    private List<FieldEntity> fields;
    @Expose
    private List<MethodEntity> methods;
    private int hookId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMaId() {
        return maId;
    }

    public void setMaId(int maId) {
        this.maId = maId;
    }

    public List<FieldEntity> getFields() {
        return fields;
    }

    public void setFields(List<FieldEntity> fields) {
        this.fields = fields;
    }

    public List<MethodEntity> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodEntity> methods) {
        this.methods = methods;
    }

    public int getHookId() {
        return hookId;
    }

    public void setHookId(int hookId) {
        this.hookId = hookId;
    }
}
