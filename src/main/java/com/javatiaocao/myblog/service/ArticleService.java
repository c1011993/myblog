package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tag;
import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: mybolg
 * @Title ArticleService
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 22:24
 * @Version 1.0
 **/
@Service
public interface ArticleService {
    DataMap insertArticle(Article article);

    DataMap getArticleManagement(int rows, int pageNum);

    DataMap deleteArticle(String id);

    Article getArticleById(String id);


    DataMap getDraftArticle(Article article, String[] tagStr, Integer tagsSizeByName);

    DataMap updateArticle(Article article);

    DataMap getMyArticles(int rows, int pageNum);


    Map<String, String> findArticleTitleByArticleId(long articleId);

    DataMap getArticleByArticleId(long articleId);
}
