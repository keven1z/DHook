package cn.com.x1001.watch;

import cn.com.x1001.Agent;
import cn.com.x1001.Config;
import cn.com.x1001.classmap.HookClass;
import cn.com.x1001.hook.HookConsts;
import cn.com.x1001.http.HttpClient;
import cn.com.x1001.util.ClassUtil;
import cn.com.x1001.util.GsonUtil;
import cn.com.x1001.util.Md5Util;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static cn.com.x1001.Agent.context;

/**
 * 新增hook类文件监控并加载到内存中
 *
 * @author keven1z
 * @date 2021/11/18
 */
public class ClassFileWatch extends Watch {
    private String canonicalPath;
    private String fileName;
    private static String last_file_md5 = "";

    public ClassFileWatch() throws IOException {
        File directory = new File(".");
        canonicalPath = directory.getCanonicalPath();
        fileName =canonicalPath + File.separator+HookConsts.CONFIG_FILE_NAME;
        Agent.out.println("[+] config path：" + fileName);
        readHook();
    }

    @Override
    public void run() {
        do {
            readHook();
            if (context.state()) {
                try {
                    getHookByServer();
                } catch (Exception e) {
                    //TODO
                    System.out.println(e.getMessage());
                }
            }
            pause(3000);
        } while (true);
    }

    /**
     * 将文件中的类加入到hook表中
     *
     * @param file 保存hook类的文件
     */
    private static void addHook(File file) {
        List<HookClass> classInfos = null;
        try {
            classInfos = ClassUtil.fileParse(file);
        } catch (IOException e) {
            Agent.out.println("解析文件出错:" + e.getMessage());
        }
        if (classInfos == null || classInfos.isEmpty()) return;

        for (HookClass hookClass : classInfos) {
            context.addHook(hookClass);
        }

    }
    public void getHookByServer() throws IOException {
        String url = Config.SERVER_HOOK+"?id="+ HookConsts.REGISTER_ID;
        HttpResponse response = HttpClient.getHttpClient().getSyn(url);
        if (response  == null || response.getStatusLine().getStatusCode() != 200) return;
        HttpEntity entity = response.getEntity();
        String content = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);

        HookClass[] ht = GsonUtil.toBean(content, HookClass[].class);
        List<HookClass> hookClasses = Arrays.asList(ht);
        if (hookClasses.isEmpty()) return;

        for (HookClass hookClass:hookClasses){
            context.addHook(hookClass);
        }
    }
    public void readHook(){
        File file = new File(fileName);
        if (!file.exists()) {
//            System.out.println("[-] 文件不存在");
            return;
        }
        String configMd5 = Md5Util.getMD5(file);
        if (!last_file_md5.equals(configMd5)) addHook(file);
        last_file_md5 = configMd5;
    }
}
