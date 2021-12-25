package cn.com.x1001.hook;

import cn.com.x1001.Agent;
import cn.com.x1001.bean.AgentInformation;
import cn.com.x1001.http.HttpClient;
import cn.com.x1001.util.GsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author keven1z
 * @date 2021/12/22
 * hook 消息的传递
 */
public class HookMessage {
    /**
     * 注册agent
     */
    public static boolean register() throws IOException {
        AgentInformation hookRegister = new AgentInformation();
        String jsonString = GsonUtil.toJsonString(hookRegister);
        HttpResponse httpResponse = HttpClient.getHttpClient().postSyn(HookConsts.SERVER_REGISTER, jsonString);
        if (httpResponse == null||httpResponse.getStatusLine().getStatusCode() != 200) {
            return false;
        }
        InputStream inputStream = httpResponse.getEntity().getContent();
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        AgentInformation agentInformation = GsonUtil.toBean(result, AgentInformation.class);
        Agent.context.register(agentInformation.getAgentId());
        return true;
    }
}
