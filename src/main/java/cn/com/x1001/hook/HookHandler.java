package cn.com.x1001.hook;


public class HookHandler {


    public static void doHook(Object[] args,String className,String method,String desc) {
        StringBuilder sb = new StringBuilder();
        for(Object arg :args){
            sb.append(arg).append("\t");
        }
        String argStr = sb.toString();
        System.out.println(className+"."+method+desc+"\n\t参数："+argStr);
    }
}
