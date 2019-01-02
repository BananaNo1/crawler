package com.crawler.demo.junit;

import com.crawler.demo.model.JdModel;
import com.crawler.demo.util.FastDfsUtils;
import com.crawler.demo.util.URLFecter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @ClassName jsoup
 * @Description TODO
 * @Author leis
 * @Date 2018/12/28 16:38
 * @Version 1.0
 **/
public class jsoup {

    @Test
    public void curl() throws IOException {
       /* String html = "First parse" + "Parsed Html into a doc.";
        Document document = Jsoup.parse(html);
        System.out.println(document);*/
        String url = "http://www.tripadvisor.com/SearchForums?q=airbnb&x=18&y=10&pid=34633&s=+";
        Document doc1 = Jsoup.connect(url).userAgent("bbb").timeout(50000).get();
        //  Elements ele = doc1.select("table[class=forumsearchresults]").select("tr[class~=firstpostrow?]");
        Elements ele = doc1.select("table[class=forumsearchresults]");
        for (Element elem : ele) {
            String _id = elem.attr("id");
            String _url = "http://www.tripadvisor.com" + elem.select("td[onclick~=setPID?]").select("a").
                    attr("href");
            String _content = elem.select("td[onclick~=setPID?]").select("a").text();
            System.out.println(_id + "====" + _url + "====" + _content);
        }

    }

    @Test
    public void jd() throws IOException {
        // HttpClient client = new DefaultHttpClient();
        CloseableHttpClient aDefault = HttpClients.createDefault();
        String url = "http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";
        String url1 = "http://search.jd.com/Search?keyword=Python&enc=utf-8&qrst=1&rt=1&stop=1&book=y&pt=1&vt=2&cid2=3287&stock=1&click=3";
        List<JdModel> bookdatas = URLFecter.URLParser(aDefault, url);
        for (JdModel jd : bookdatas) {
            System.out.println(jd.toString());
        }
    }

    @Test
    public void jd2() throws IOException {
        String url1 = "http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&psort=3&page=3";//第二页商品

        Document doc = Jsoup.connect(url1).get();
        Elements img = doc.getElementsByTag("img");
        //doc获取整个页面的所有数据
        Elements ulList = doc.select("ul[class='gl-warp clearfix']").select("li[class=gl-item]");

        for (Element element : img) {
            String src = element.attr("source-data-lazy-img");
            if (StringUtils.isEmpty(src)) {
                continue;
            }
            src = "http:" + src;
            download("E:/image", src);
        }


    }
    // http://img14.360buyimg.com/n1/s200x200_jfs/t17953/201/1450663539/451183/3262b8de/5acb3627N8191c867.jpg

    public void download(String filePath, String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/"));
        File files = new File(filePath);
        if (!files.exists()) {
            files.mkdirs();
        }
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            File file = new File(filePath + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            int i = 0;
            while ((i = is.read()) != -1) {
                fos.write(i);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void jd3() throws IOException {
        String url = "http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("gl-item");

        JdModel jdModel = new JdModel();
        for (Element element : elements) {
            String src = element.select("div[class=p-img]").select("a").select("img").attr("source-data-lazy-img");
            String id = element.attr("data-sku");
            String bookPrice = element.select("div[class=p-price]").select("strong").select("i").text();
            String bookName = element.select("div[class=p-name]").select("em").text();
            src = "http:" + src;
           // download("E:/image", src);
            String upload = FastDfsUtils.upload("E:/image/bb9b79a2e13592f0.jpg");
            jdModel.setBookID(id).setBookPrice(bookPrice).setBookName(bookName).setImgUrl(upload);
            System.out.println(jdModel);
        }
    }

    @Test
    public void test(){
        BigDecimal decimal   = new BigDecimal(57.90+"");
    }

}
