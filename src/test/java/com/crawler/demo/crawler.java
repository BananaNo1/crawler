package com.crawler.demo;

import com.crawler.demo.entity.Book;
import com.crawler.demo.entity.Mobile;
import com.crawler.demo.junit.webcollector;
import com.crawler.demo.mapper.BookMapper;
import com.crawler.demo.mapper.MobileMapper;
import com.crawler.demo.model.JdModel;
import com.crawler.demo.repository.EsRepository;
import com.crawler.demo.util.FastDfsUtils;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
 * @ClassName crawler
 * @Description TODO
 * @Author leis
 * @Date 2019/1/2 11:12
 * @Version 1.0
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class crawler {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private MobileMapper mobileMapper;

    @Autowired
    private EsRepository esRepository;

    @Test
    public void insert() throws IOException {
        String url = "http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("gl-item");

        Book book = new Book();
        for (Element element : elements) {
            String src = element.select("div[class=p-img]").select("a").select("img").attr("source-data-lazy-img");
            String id = element.attr("data-sku");
            String bookPrice = element.select("div[class=p-price]").select("strong").select("i").text();
            String bookName = element.select("div[class=p-name]").select("em").text();

            src = "http:" + src;
            String downloadUrl = download("E:/image", src);
            if (downloadUrl != null) {
                String upload = FastDfsUtils.upload("E:/image/" + downloadUrl);
                book.setBookImgUrl(upload.replace("M00", ""));
            }

            bookPrice = bookPrice.trim();
            book.setBookId(Long.parseLong(id)).setBookName(bookName).setBookPrice(new BigDecimal(bookPrice));
            book.setBookComment(0);
            //bookMapper.insertSelective(book);
            System.out.println(book);
        }

    }

    @Test
    public void crawlerPhone() throws IOException {
        String url = "https://list.jd.com/list.html?cat=9987,653,655";
        Document doc = Jsoup.connect(url).get();
        Elements elementsByClass = doc.getElementsByClass("gl-item");
        for (Element element : elementsByClass) {
            String img = element.select("div[class=p-img]").select("a").select("img").attr("src");
            String img1 = element.select("div[class=p-img]").select("a").select("img").attr("data-lazy-img");
            String name = element.select("div[class=p-name]").select("em").text();

            String subImage = element.select("div[class=p-scroll]").select("div[class=ps-wrap]").select("[class=ps-item]").select("a").select("img").attr("src");
          //  System.out.println(subImage);

            String src = StringUtils.isEmpty(img) ? img1 : img;
            src = "http:" + src;
//            String downloadUrl = download("E:/image", src);
//            System.out.println(downloadUrl);
            System.out.println(name);
            Mobile mobile = new Mobile();
            mobile.setMainImage(src);
            mobile.setName(name);
            mobile.setStock(0);
            BigDecimal bigDecimal =new BigDecimal("4531");
            mobile.setPrice(bigDecimal);
            System.out.println(mobile.getName());
            mobileMapper.insertSelective(mobile);
        }
    }

    @Test
    public void inse(){
        Mobile mobile =new Mobile();

    }

    //    @Test
//    public void saveEs() {
//        List<Book> books = bookMapper.selectAll();
//        for (Book book : books) {
//            esRepository.save(book);
//        }
//    }
//
    @Test
    public void search() {
        Iterable<Book> search = esRepository.search(new MatchQueryBuilder("bookName", "编程"));
        search.forEach(book -> {
            System.out.println(book);
        });
        System.out.println("**********");
        Book book = esRepository.queryBookById(184);
        System.out.println(book);
//        esRepository.deleteAll();
//        Iterable<Book> all = esRepository.findAll();
//        all.forEach(book -> {
//            System.out.println(book);
//        });
    }


    @Test
    public void test() throws IOException {
        String url = "http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("gl-item");
        for (Element element : elements) {
            Elements select = element.select("div[class=p-commit]");
            Elements strong = select.select("strong");
            Elements a = strong.select("a");
            String text = a.text();
            String val = a.val();
        }
    }

//
//    @Test
//    public void delete() {
//        List<Book> books = bookMapper.selectAll();
//        for (Book book : books) {
//            FastDfsUtils.delete(book.getBookImgUrl());
//        }
//    }


    public String download(String filePath, String imageUrl) {
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
            return fileName;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testWebcollector() throws Exception {
        webcollector webcollector = new webcollector("craw",true);
        webcollector.start(4);
    }
}
