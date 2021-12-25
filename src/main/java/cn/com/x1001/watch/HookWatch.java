package cn.com.x1001.watch;

import cn.com.x1001.Agent;
import cn.com.x1001.bean.HookTmp;
import cn.com.x1001.classmap.HookClass;
import cn.com.x1001.hook.HookConsts;
import cn.com.x1001.http.HttpClient;
import cn.com.x1001.util.GsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

/**
 * hook点的监控，当发现存在未hook类，自动加入hook
 */
public class HookWatch extends Watch {
    private Instrumentation instrumentation;
    public HookWatch(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public void run() {
        while (true) {
            Set<HookClass> classHashSet = Agent.context.getClassHashSet();
            for (HookClass hookClass :classHashSet){
                if (hookClass.isHooked()) continue;
                try {
                    reTransformClass(hookClass.getClassName());
                } catch (UnmodifiableClassException e) {
                    System.out.println("transform class error:"+e.getMessage());
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void reTransformClass(String transformClassName) throws UnmodifiableClassException {
        Class[] loadedClasses = instrumentation.getAllLoadedClasses();
        for (Class clazz : loadedClasses) {
//            if (clazz.isInterface()) continue;
            String className = clazz.getName().replace(".", "/");
            if (className.equals(transformClassName)) {
                instrumentation.retransformClasses(clazz);
            }

        }
    }
}
