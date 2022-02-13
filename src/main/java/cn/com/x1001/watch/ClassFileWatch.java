package cn.com.x1001.watch;

import cn.com.x1001.Agent;
import cn.com.x1001.bean.HookTmp;
import cn.com.x1001.classmap.HookClass;
import cn.com.x1001.InstrumentationContext;
import cn.com.x1001.hook.HookConsts;
import cn.com.x1001.util.ClassUtil;
import cn.com.x1001.util.HookUtil;
import cn.com.x1001.util.Md5Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * 新增hook类文件监控并加载到内存中
 *
 * @author keven1z
 * @date 2021/11/18
 */
public class ClassFileWatch extends Thread {
    private String canonicalPath;
    private String fileName;
    private static String last_file_md5 = "";

    public ClassFileWatch() throws IOException {
        File directory = new File(".");
        canonicalPath = directory.getCanonicalPath();
        fileName ="C:\\Users\\ziizh\\Desktop\\" + HookConsts.CSV_FILE_NAME;
        readConfig();
        Agent.out.println("配置文件读取路径：" + fileName);

    }

    @Override
    public void run() {
        pause(10000);
        while (true) {
            readConfig();
            pause(3000);
        }
    }


    private void pause(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件中的类加入到hook表中
     *
     * @param file 保存hook类的文件
     */
    private static void addHook(File file) {
        Set<HookTmp> classInfos = null;
        try {
            classInfos = ClassUtil.fileParse(file);
        } catch (IOException e) {
            Agent.out.println("解析文件出错:" + e.getMessage());
        }

        if (classInfos == null || classInfos.isEmpty()) return;

        for (HookTmp hookTmp : classInfos) {
            addToHookClass(hookTmp);
            Agent.out.println("load hook：" + hookTmp);
        }

    }

    /**
     * @param hookTmp hook class临时对象
     */
    private static void addToHookClass(HookTmp hookTmp) {
        String className = hookTmp.getClassName();
        String method = hookTmp.getMethod();
        String desc = hookTmp.getDesc();
        String returnValue = hookTmp.getReturnValue();
        HashMap<Integer, String> parameters = hookTmp.getParameters();
        InstrumentationContext context = Agent.context;
        HookClass hookClass;
        if (context.isHookClass(className, method, desc)) {
            hookClass = context.getHookClass(className, method, desc);
        } else {
            hookClass = new HookClass();
            hookClass.setClassName(className);
            hookClass.setMethod(method);
            hookClass.setDesc(desc);
            context.addHook(hookClass);
        }
        hookClass.setActions(hookTmp.getActions());
        hookClass.setReturnValue(returnValue);
        hookClass.setParameters(parameters);
    }
    public void readConfig(){

        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        String configMd5 = Md5Util.getMD5(file);
        if (!last_file_md5.equals(configMd5)) addHook(file);
        last_file_md5 = configMd5;

    }
}
