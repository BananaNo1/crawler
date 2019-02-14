package com.crawler.demo.junit;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import com.crawler.demo.entity.Mobile;
import com.crawler.demo.mapper.MobileMapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

/**
 * @ClassName webcollector
 * @Description TODO
 * @Author leis
 * @Date 2019/2/14 15:05
 * @Version 1.0
 **/
public class webcollector extends BreadthCrawler {

    @Autowired
    MobileMapper mobileMapper;


    public webcollector(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        this.addSeed("https://list.jd.com/list.html?cat=9987,653,655");
        for (int pageIndex = 2; pageIndex <= 5; pageIndex++) {
            String seedUrl = String.format("https://list.jd.com/list.html?cat=9987,653,655&page=%d", pageIndex);
            this.addSeed(seedUrl);
        }

        setThreads(50);
        getConf().setTopN(100);

    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        String url = page.url();
        if (url != null) {
//            .select("div[class=p-img]").select("a").select("img").attr("src");
            Elements select = page.select("[class=gl-item]");
            for (Element element : select) {
                String img = element.select("div[class=p-img]").select("a").select("img").attr("src");
                String img1 = element.select("div[class=p-img]").select("a").select("img").attr("data-lazy-img");
                String subImage = element.select("div[class=p-scroll]").select("div[class=ps-wrap]").select("[class=ps-item]").select("a").select("img").attr("src");
                String name = element.select("div[class=p-name]").select("em").text();
                String src = StringUtils.isEmpty(img) ? img1 : img;
//                System.out.println(src);
//                System.out.println(name);
                Mobile mobile = new Mobile();
                mobile.setName(name);
                mobile.setMainImage(src);
                mobileMapper.insertSelective(mobile);
            }
        }
    }

}
