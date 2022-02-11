package cn.com.x1001.watch;

import cn.com.x1001.Agent;
import cn.com.x1001.Config;
import cn.com.x1001.InstrumentationContext;
import cn.com.x1001.bean.ClassMapEntity;
import cn.com.x1001.http.HttpClient;
import cn.com.x1001.util.GsonUtil;
import org.apache.http.HttpResponse;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author keven1z
 * @date 2022/02/09
 */
public class ClassMapWatch extends Watch{
    /**
     * classMap 发送的阈值
     */
    private static int LIMIT = 50;
    @Override
    public void run() {
        InstrumentationContext context = Agent.context;
        CopyOnWriteArraySet<ClassMapEntity> classMapView = context.classMapView;
        /*
        * 当classmap的数量大于50的时候，发送到服务端，如果尝试5次都没有超过50，则直接发送
        */
        int tryCount = 5;
        while (true){
            if (classMapView.isEmpty()){
                pause(5000);
                continue;
            }

            int size = classMapView.size();

            if (tryCount == 0) {
                if (sendClassMap(classMapView)){
                    classMapView.clear();
                }
                tryCount = 5;
                continue;
            }

            if (size >= LIMIT){
                if (sendClassMap(classMapView)){
                    classMapView.clear();
                }
            }
            else {
                tryCount --;
            }
            pause(5000);
        }
    }

    private boolean sendClassMap(CopyOnWriteArraySet<ClassMapEntity> classMapView){
        String jsonString = GsonUtil.toJsonString(classMapView);
        try {
            HttpResponse httpResponse = HttpClient.getHttpClient().post(Config.SERVER_CLASS_MAP, jsonString);
            return httpResponse.getStatusLine().getStatusCode() == 200;
        } catch (Exception e) {
            //TODO
        }
        return false;
    }
}
