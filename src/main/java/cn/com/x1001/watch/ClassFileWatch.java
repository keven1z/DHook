package cn.com.x1001.watch;

import cn.com.x1001.Agent;
import cn.com.x1001.bean.HookTmp;
import cn.com.x1001.classmap.HookClass;
import cn.com.x1001.InstrumentationContext;
import cn.com.x1001.hook.HookConsts;
import cn.com.x1001.http.HttpClient;
import cn.com.x1001.util.ClassUtil;
import cn.com.x1001.util.GsonUtil;
import cn.com.x1001.util.HookUtil;
import cn.com.x1001.util.Md5Util;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    private static List<HookTmp> lastHookTmps = new ArrayList<>();

    public ClassFileWatch() throws IOException {
        File directory = new File(".");
        canonicalPath = directory.getCanonicalPath();
        fileName =canonicalPath + File.separator+HookConsts.CSV_FILE_NAME;
        Agent.out.println("配置文件读取路径：" + fileName);

    }

    @Override
    public void run() {
        do {
            readHook();
            pause(3000);
        } while (true);
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
        }

    }
    public void getHookByServer() throws IOException {
        HttpResponse response = HttpClient.getHttpClient().getSyn(HookConsts.SERVER_HOOK);
        if (response  == null || response.getStatusLine().getStatusCode() != 200) return;
        HttpEntity entity = response.getEntity();
        String content = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
        HookTmp[] ht = GsonUtil.toBean(content, HookTmp[].class);
        List<HookTmp> hookTmps = Arrays.asList(ht);
        if (hookTmps.isEmpty()) return;

        for (HookTmp hookTmp:hookTmps){
            if (!lastHookTmps.contains(hookTmp)){
                Agent.out.println("[+] load hook：" + hookTmp);
                addToHookClass(hookTmp);
            }
        }
        lastHookTmps = hookTmps;
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
    public void readHook(){
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        String configMd5 = Md5Util.getMD5(file);
        if (!last_file_md5.equals(configMd5)) addHook(file);
        last_file_md5 = configMd5;

    }

    public static void main(String[] args) throws IOException {
        new ClassFileWatch().getHookByServer();
    }
}
