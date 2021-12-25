package cn.com.x1001.http;


import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author keven1z
 * @date 2021/12/22
 */
public class HttpClient {
    private static HttpClient httpClient;
    private static CloseableHttpAsyncClient request;
    private static CloseableHttpClient requestSyn;
    public static final int TIMEOUT = 1000;
    private HttpClient(){
        request = HttpAsyncClients.custom().build();
        requestSyn = HttpClients.createDefault();
    }
    public static HttpClient getHttpClient(){
        if (httpClient == null) httpClient = new HttpClient();
        return httpClient;
    }
    public HttpResponse getSyn(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT)
                .build();
        get.setConfig(config);
        CloseableHttpResponse httpResponse = requestSyn.execute(get);
        return httpResponse;
    }
    public void get(String url){
        request.start();
        HttpGet get = new HttpGet(url);
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT)
                .build();
        get.setConfig(config);
        request.execute(get,null);
    }
    public void post(String url,String postJson) throws UnsupportedEncodingException {
        if (postJson == null) return;
        request.start();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(postJson));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        request.execute(httpPost,null);
    }

    /**
     * 同步post请求
     */
    public HttpResponse postSyn(String url, String postJson) throws IOException {
        if (postJson == null) return null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(postJson));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse httpResponse = requestSyn.execute(httpPost);
        return httpResponse;
    }
    public static void close() throws IOException {
        if (request != null) {
            request.close();
        }
    }
}
