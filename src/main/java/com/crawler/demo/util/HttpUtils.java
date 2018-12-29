package com.crawler.demo.util;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;

import java.io.IOException;

/**
 * @ClassName HttpUtils
 * @Description TODO
 * @Author leis
 * @Date 2018/12/28 16:52
 * @Version 1.0
 **/
public class HttpUtils {

    public static HttpResponse getRowHtml(HttpClient client, String personalUrl) {
        HttpGet getMethod = new HttpGet(personalUrl);
        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
        try {
            client.execute(getMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
