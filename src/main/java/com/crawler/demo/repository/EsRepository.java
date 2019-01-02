package com.crawler.demo.repository;

import com.crawler.demo.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @ClassName EsRepository
 * @Description TODO
 * @Author leis
 * @Date 2019/1/2 15:29
 * @Version 1.0
 **/
@Component
public interface EsRepository extends ElasticsearchRepository<Book, Integer> {

    Book queryBookById(Integer id);
}
