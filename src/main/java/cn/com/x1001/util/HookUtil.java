package cn.com.x1001.util;

import cn.com.x1001.classmap.HookClass;

import java.util.Set;

/**
 * @author keven1z
 * @date 2021/11/29
 */
public class HookUtil {
    public static boolean isContainMethod(Set<HookClass> hookClasses, String method) {
        for (HookClass hookClass : hookClasses) {
            String m = hookClass.getMethod();
            if(m.equals(method)) return true;
        }
        return false;
    }

    public static boolean isContainMethodDesc(Set<HookClass> hookClasses, String method,String desc) {
        for (HookClass hookClass : hookClasses) {
            String m = hookClass.getMethod();
            String d = hookClass.getDesc();
            if(m.equals(method) && d.equals(desc)) return true;
        }
        return false;
    }

}
