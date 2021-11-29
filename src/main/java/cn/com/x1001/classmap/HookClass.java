package cn.com.x1001.classmap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author keven1z
 * @Date 2021/6/11
 * @Description hook类的信息
 */
public class HookClass {
    private String className;
    private boolean isHooked = false;
    private Set<Integer> actions = new HashSet<>();
    private String method;
    private String desc;
    private String returnValue;
    private HashMap<Integer, String> parameters = new HashMap<>();

    public HookClass() {
    }



    public HookClass(String className, String method, String desc, String returnValue, HashMap<Integer, String> parameters,Set<Integer> actions) {
        this.className = className;
        this.actions = actions;
        this.method = method;
        this.desc = desc;
        this.returnValue = returnValue;
        this.parameters = parameters;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public void setActions(Set<Integer> actions) {
        this.actions.addAll(actions);
    }

    public Set<Integer> getActions() {
        return actions;
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

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public HashMap<Integer, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<Integer, String> parameters) {
        this.parameters = parameters;
    }

    public void setParameters(int pos, String value) {
        this.parameters.put(pos, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HookClass hookClass = (HookClass) o;
        return className.equals(hookClass.className) && method.equals(hookClass.method) && Objects.equals(desc, hookClass.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, method, desc);
    }

    public boolean isHooked() {
        return isHooked;
    }

    public void setHooked(boolean hooked) {
        isHooked = hooked;
    }

    @Override
    public String toString() {
        return className + "." + method + desc;
    }
    public void cloneHook(HookClass hookClass){
        this.setParameters(hookClass.getParameters());
        this.setMethod(hookClass.getMethod());
    }

}
