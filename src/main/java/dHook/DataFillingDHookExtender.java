package dHook;

/**
 * @author keven1z
 * @date 2022/06/24
 */
public abstract class DataFillingDHookExtender {
    private String className;
    private String method;
    private String desc;
    public void init(String className,String method,String desc){
        this.className = className;
        this.method = method;
        this.desc = desc;
    }

    public String getClassName() {
        return className;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }
}
