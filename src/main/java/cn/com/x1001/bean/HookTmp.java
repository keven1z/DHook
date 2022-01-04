package cn.com.x1001.bean;

import cn.com.x1001.hook.HookConsts;
import cn.com.x1001.util.Md5Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author keven1z
 * @date 2021/11/18
 */
public class HookTmp {
    private String id;
    private String className;
    private String method;
    private String desc;
    private String returnValue;
    private Set<Integer> actions = new HashSet<>();
    private HashMap<Integer, String> parameters = new HashMap<>();
    private String md5;

    public HookTmp(String className, String method, String desc, String parameter) {
        this.className = parseAction(className);
        this.method = method;
        this.desc = desc;
        this.parameters = parseParameter(parameter);
        this.md5 = getMd5();
    }

    private String parseAction(String className) {
        if (className.startsWith(HookConsts.FLAG_ACTION_GET_DECOMPILER)) {
            setActions(HookConsts.ACTION_GET_DECOMPILER);
            return className.replace(HookConsts.FLAG_ACTION_GET_DECOMPILER, "");
        }
        return className;
    }

    private HashMap<Integer, String> parseParameter(String parameter) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        if (parameter == null) return hashMap;

        String[] parameterArr = parameter.split(";");
        for (String p : parameterArr) {
            String[] pv = p.split("-");
            if (pv.length != 2) continue;
            int pos = Integer.parseInt(pv[0]);
            String value = pv[1];
            hashMap.put(pos, value);
        }
        return hashMap;
    }

    public Set<Integer> getActions() {
        return actions;
    }

    public HashMap<Integer, String> getParameters() {
        return parameters;
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
                ", desc='" + desc;
    }
    public String getMd5(){
        if (md5 != null) return md5;
        StringBuilder sb = new StringBuilder();
        sb.append(className).append(method).append(desc);
        if (returnValue != null) sb.append(returnValue);
        String md5 = Md5Util.getMD5(sb.toString());
        return md5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HookTmp hookTmp = (HookTmp) o;
        return md5.equals(hookTmp.md5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(md5);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
