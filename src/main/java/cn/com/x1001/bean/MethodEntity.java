package cn.com.x1001.bean;

import com.google.gson.annotations.Expose;


import java.io.Serializable;

/**
 * @author keven1z
 * @date 2022/01/07
 * 静态方法
 */
public class MethodEntity implements Serializable {
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
    private int sort;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
