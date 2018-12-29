package com.crawler.demo.util;

import com.crawler.demo.model.JdModel;
import com.crawler.demo.parse.JdParse;
import com.google.common.collect.Lists;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName URLFecter
 * @Description TODO
 * @Author leis
 * @Date 2018/12/28 16:49
 * @Version 1.0
 **/
public class URLFecter {

    public static List<JdModel> URLParser(CloseableHttpClient client, String url) throws IOException {
        List<JdModel> list = Lists.newArrayList();

        // HttpResponse response = HttpUtils.getRowHtml(client, url);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        int StatusCode = response.getStatusLine().getStatusCode();
        if (StatusCode == 200) {
            HttpEntity entity1 = response.getEntity();
            String entity = EntityUtils.toString(response.getEntity(), "utf-8");
            list = JdParse.getData(entity);
        } else {
            //否则，消耗掉实体
            EntityUtils.consume(response.getEntity());
        }
        return list;
    }
}
