package com.crawler.demo.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @ClassName JdModel
 * @Description TODO
 * @Author leis
 * @Date 2018/12/28 16:47
 * @Version 1.0
 **/
@Data
@ToString
@Accessors(chain = true)
public class JdModel {
    private String bookID;
    private String bookName;
    private String bookPrice;
    private String imgUrl;
}
