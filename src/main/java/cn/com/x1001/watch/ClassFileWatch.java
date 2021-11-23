package cn.com.x1001.watch;

import cn.com.x1001.Agent;
import cn.com.x1001.bean.HookTmp;
import cn.com.x1001.classmap.ClassInfo;
import cn.com.x1001.InstrumentationContext;
import cn.com.x1001.hook.HookConsts;
import cn.com.x1001.util.ClassUtil;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * 新增hook类文件监控并加载到内存中
 * @author keven1z
 * @date 2021/11/18
 */
public class ClassFileWatch extends Thread{
    private String canonicalPath;
    public ClassFileWatch() throws IOException {
        File directory = new File(".");
        canonicalPath = directory.getCanonicalPath();
    }
    @Override
    public void run() {
        String fileName = canonicalPath +File.separator+ HookConsts.CSV_FILE_NAME;
        File file = new File(fileName);
        Agent.out.println("文件路径："+file.getAbsolutePath());
        while(true){
            if (!file.exists()){
                pause(5000);
                continue;
            }
            addHook(file);
            pause(3000);
        }
    }

    private void pause(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件中的类加入到hook表中
     * @param file 保存hook类的文件
     */
    private static void addHook(File file){
        Set<HookTmp> classInfos = null;
        try {
            classInfos = ClassUtil.fileParse(file);
        } catch (IOException e) {
            Agent.out.println("解析文件出错:"+e.getMessage());
        }

        if (classInfos == null || classInfos.isEmpty()) return;

        for (HookTmp hookTmp:classInfos){
            if (hasHooked(hookTmp.getClassName(),hookTmp.getMethod(),hookTmp.getDesc())){
                continue;
            }
            addToHookClass(hookTmp);
            Agent.out.println("load hook："+hookTmp);
        }

    }

    private static boolean hasHooked(String className,String method,String desc){
        Set<ClassInfo> classHashSet = Agent.context.getClassHashSet();
        for (ClassInfo hookClass:classHashSet){
            if (hookClass.exist(className,method,desc)) return true;
        }
        return false;
    }

    /**
     * @param hookTmp hook class临时对象
     */
    private static void addToHookClass(HookTmp hookTmp){
        String className = hookTmp.getClassName();
        String method = hookTmp.getMethod();
        String desc = hookTmp.getDesc();
        String returnValue = hookTmp.getReturnValue();
        InstrumentationContext context = Agent.context;
        ClassInfo classInfo;
        if(context.isHookClass(className)){
            classInfo = context.getHookClass(className);
            classInfo.setMethodDesc(method,desc,returnValue);

        }
        else {
            classInfo = new ClassInfo();
            classInfo.setClassName(className);
            classInfo.setMethodDesc(method,desc,returnValue);
            context.addHook(classInfo);
        }
    }
}
