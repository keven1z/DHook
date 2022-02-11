package cn.com.x1001.bean;

import com.google.gson.annotations.Expose;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author keven1z
 * @date 2022/01/07
 * 静态方法
 */
public class MethodEntity implements Serializable, Comparator<MethodEntity> {
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

    @Override
    public int compare(MethodEntity methodEntity1,MethodEntity methodEntity2) {
        if (methodEntity1.getSort()> methodEntity2.getSort()) return 1;
        else if(methodEntity1.getSort()== methodEntity2.getSort()) return 0;
        return -1;
    }

    @Override
    public String toString() {
        return "MethodEntity{" +
                "sort=" + sort +
                '}';
    }

    public static void main(String[] args) {
        MethodEntity method = new MethodEntity();
        method.setSort(1);
        MethodEntity method1 = new MethodEntity();
        method1.setSort(3);
        method1.setClassName("aaaaa");
        MethodEntity method2 = new MethodEntity();
        method2.setSort(2);
        ArrayList<MethodEntity> arrayList = new ArrayList<>();
        arrayList.add(method);
        arrayList.add(method1);
        arrayList.add(method2);
        arrayList.sort(new MethodEntity());
        System.out.println(arrayList);
    }
}
