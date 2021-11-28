package cn.com.x1001.hook;


import cn.com.x1001.Agent;
import cn.com.x1001.util.StackTrace;

public class HookHandler {


    public static void doHook(Object returnValue, Object[] args, String className, String method, String desc) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg).append("\t");
        }
        String argStr = sb.toString();
        System.out.println(className + "." + method + desc + "\n\t参数：" + argStr);
        if (returnValue != null) {
            Agent.out.println("\t返回值：" + returnValue);
        }
//        Agent.out.println(StackTrace.getStackTrace());
    }
}