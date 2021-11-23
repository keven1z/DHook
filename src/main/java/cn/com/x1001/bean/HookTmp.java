package cn.com.x1001.bean;

/**
 * @author keven1z
 * @date 2021/11/18
 */
public class HookTmp {
    private String ClassName;
    private String method;
    private String desc;
    private String returnValue;

    public HookTmp(String className, String method, String desc) {
        ClassName = className;
        this.method = method;
        this.desc = desc;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
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

    @Override
    public String toString() {
        return "ClassName='" + ClassName + '\'' +
                ", method='" + method + '\'' +
                ", desc='" + desc ;
    }
}
