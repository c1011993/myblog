package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: mybolg
 * @Title ArticleMapper
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 22:31
 * @Version 1.0
 **/
@Mapper
@Repository
public interface ArticleMapper {
    void saveArticle(Article article);

    List<Article> getArticleManagement();

    Article getArticleById(String id);

    void updateLastNextId(@Param(value = "lastOrNextStr") String lastOrNextStr, @Param(value = "updateId") Long
            updateId, @Param(value = "articleId") Long articleId);

    void deleteArticle(Long articleId);


    void updateArticle(Article article);

    String getArticleTitleByArticleId(Long articleId);

    String getArticleAuthorByArticleId(Long articleId);

    Article findArticleTitleByArticleId(long articleId);

    Article getArticleByArticleId(Long articleId);
}
