package cn.com.x1001.bean;

import com.google.gson.annotations.Expose;


import java.io.Serializable;
import java.util.List;

/**
 * @author keven1z
 * @date 2022/01/07
 */
public class MethodActionEntity implements Serializable {
//    /*
//     * 1: onMethodEnter
//     * 2: onMethodExit
//     */
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
}
