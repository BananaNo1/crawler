package com.crawler.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "book")
public class Book {

    @Id
    private Integer id;

    @Field(type = FieldType.Long)
    private Long bookId;

    @Field(searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String bookName;

    private BigDecimal bookPrice;
    
    private String bookImgUrl;

    private Integer bookComment;

}