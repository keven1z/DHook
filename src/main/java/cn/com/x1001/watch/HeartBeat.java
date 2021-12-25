package cn.com.x1001.watch;

import cn.com.x1001.http.NettyClient;

/**
 * @author keven1z
 * @date 2021/12/24
 */
public class HeartBeat extends Watch{
    private String agentId;
    public HeartBeat(String agentId){
        this.agentId = agentId;
    }
    @Override
    public void run() {
        try {
            NettyClient.start(this.agentId);
        } catch (InterruptedException e) {
            //TODO log
        }
    }
}
