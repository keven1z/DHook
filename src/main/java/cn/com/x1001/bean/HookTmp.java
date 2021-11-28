package cn.com.x1001.bean;

import cn.com.x1001.hook.HookConsts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author keven1z
 * @date 2021/11/18
 */
public class HookTmp {
    private String className;
    private String method;
    private String desc;
    private String returnValue;
    private Set<Integer> actions = new HashSet<>();
    private HashMap<Integer,String> parameters = new HashMap<>();

    public HookTmp(String className, String method, String desc) {
        this.className = parseAction(className);
        this.method = method;
        this.desc = desc;
    }

    private String parseAction(String className) {
        if (className.startsWith(HookConsts.FLAG_ACTION_GET_DECOMPILER)){
            setActions(HookConsts.ACTION_GET_DECOMPILER);
            return className.replace(HookConsts.FLAG_ACTION_GET_DECOMPILER,"");
        }
        return className;
    }

    public Set<Integer> getActions() {
        return actions;
    }
    public void addParameter(int pos, String value) {
        this.parameters.put(pos, value);
    }
    public void setActions(Set<Integer> actions) {
        this.actions.addAll(actions);
    }

    public void setActions(int action) {
        this.actions.add(action);
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
        return "ClassName='" + className + '\'' +
                ", method='" + method + '\'' +
                ", desc='" + desc ;
    }
}
