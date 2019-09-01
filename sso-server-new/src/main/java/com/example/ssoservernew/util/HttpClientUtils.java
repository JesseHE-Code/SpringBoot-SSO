package com.example.ssoservernew.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @Author :hezhiqiang
 * @Date :2019-08-29 12:06
 **/

public class HttpClientUtils {

    private static RequestConfig requestConfig = RequestConfig.custom()
            //从连接池中获取连接的超时时间
            .setConnectionRequestTimeout(15000)
            //与服务器连接超时时间
            .setConnectTimeout(15000)
            //socket读数据超时时间
            .setSocketTimeout(15000)
            .build();

    public static String sendHttp(HttpRequestMethedEnum requestMethod, String url, Map<String, Object> params, Map<String, String> header) {
        //创建一个HttpClient对象;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String responseContent = null;
        //创建一个Http请求对象并设置请求的URL
        HttpRequestBase request = requestMethod.createRequest(url);
        request.setConfig(requestConfig);
        //设置请求对象的请求头参数
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 添加请求参数，POST方法适用，GET方法需要使用字符串拼接
        try {
            if (params != null) {
                HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("file", (FileBody) params.get("file")).build();
                ((HttpEntityEnclosingRequest) request).setEntity(reqEntity);
            }

            httpResponse = httpClient.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                responseContent = EntityUtils.toString(httpEntity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭响应对象;
                if (httpResponse != null) {
                    httpResponse.close();
                }
                //关闭HttpClient.
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
}