package com.javatiaocao.myblog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @program: mybolg
 * @Title StatisticsMapper
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-30 10:07
 * @Version 1.0
 **/
@Mapper
@Repository
public interface StatisticsMapper {

    int getAllVisitor();

    int getYesterdayVisitor();

    int getArticleNum();

    int getArticleThumbsUpNum();

    int getAllUser();
}
