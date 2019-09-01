package com.example.ssoservernew.util;

import org.apache.http.client.methods.*;

/**
 * @Author :hezhiqiang
 * @Date :2019-08-29 12:06
 **/

public enum HttpRequestMethedEnum {

    // HttpGet请求
    HttpGet {
        @Override
        public HttpRequestBase createRequest(String url) { return new HttpGet(url); }
    },
    // HttpPost 请求
    HttpPost {
        @Override
        public HttpRequestBase createRequest(String url) { return new HttpPost(url); }
    },
    // HttpPut 请求
    HttpPut {
        @Override
        public HttpRequestBase createRequest(String url) { return new HttpPut(url); }
    },
    // HttpDelete 请求
    HttpDelete {
        @Override
        public HttpRequestBase createRequest(String url) { return new HttpDelete(url); }
    };

    public HttpRequestBase createRequest(String url) { return null; }
}
