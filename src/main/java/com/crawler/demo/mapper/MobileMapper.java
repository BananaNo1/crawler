package com.crawler.demo.mapper;

import com.crawler.demo.entity.Mobile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MobileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mobile record);

    int insertSelective(Mobile record);

    Mobile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mobile record);

    int updateByPrimaryKey(Mobile record);
}