package com.crawler.demo.parse;

import com.crawler.demo.model.JdModel;
import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * @ClassName JdParse
 * @Description TODO
 * @Author leis
 * @Date 2018/12/28 17:11
 * @Version 1.0
 **/
public class JdParse {
    public static List<JdModel> getData(String html){
        List<JdModel> data = Lists.newArrayList();
        Document doc = Jsoup.parse(html);
        Elements elements=doc.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");
        for (Element ele:elements) {
            String bookID=ele.attr("data-sku");
            String bookPrice=ele.select("div[class=p-price]").select("strong").select("i").text();
            String bookName=ele.select("div[class=p-name]").select("em").text();
            //创建一个对象，这里可以看出，使用Model的优势，直接进行封装
            JdModel jdModel=new JdModel();
            //对象的值
            jdModel.setBookID(bookID);
            jdModel.setBookName(bookName);
            jdModel.setBookPrice(bookPrice);
            //将每一个对象的值，保存到List集合中
            data.add(jdModel);
        }
        return data;
    }
 }
