package com.javatiaocao.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringAndArray;
import com.javatiaocao.myblog.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: mybolg
 * @Title ArticleServiceIml
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 22:25
 * @Version 1.0
 **/
@Service
public class ArticleServiceIml implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public DataMap insertArticle(Article article) {
        //处理非空字段（没传入的）
        //todo

        if (StringUtil.BLANK.equals(article.getOriginalAuthor())) {
            article.setOriginalAuthor(article.getAuthor());
        }
        if (StringUtil.BLANK.equals(article.getArticleUrl())) {
            //拼接一个url，生产环境：www.javatiaozao.com
            article.setArticleUrl("www.javatiaozao.com" + "/article/" + article.getArticleId());
        }
        if (StringUtil.BLANK.equals(article.getLikes())) {
            article.setLikes(0);
        }
        //todo 设置上一篇文章id
        articleMapper.saveArticle(article);
        //给controller层响应,显示在执行成功页面上的
        //articleTitle updateDate articleUrl author
        HashMap<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("articleTitle", article.getArticleTitle());
        dataMap.put("updateDate", article.getUpdateDate());
        dataMap.put("articleUrl", "/article/" + article.getArticleId());
        dataMap.put("author", article.getAuthor());

        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getArticleManagement(int rows, int pageNum) {
        //打开分页插件
        PageHelper.startPage(rows, pageNum);
        //查询文章存入集合中
        List<Article> articles = articleMapper.getArticleManagement();
        PageInfo<Article> pageInfo = new PageInfo<>();
        //返回数据处理
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJsonObject = new JSONObject();
        //创建
        JSONObject articleJson;
        //obj['id'] + obj['articleId'] obj['articleTitle'] obj['publishDate'] obj['articleCategories']  obj['visitorNum']

        for (Article article : articles) {
            articleJson = new JSONObject();
            articleJson.put("id", article.getId());
            articleJson.put("articleId", article.getArticleId());
            articleJson.put("originalAuthor", article.getOriginalAuthor());
            articleJson.put("articleTitle", article.getArticleTitle());
            articleJson.put("publishDate", article.getPublishDate());
            articleJson.put("visitorNum", "99");
            returnJsonArray.add(articleJson);
        }
        returnJsonObject.put("result", returnJsonArray);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());

        return DataMap.success().setData(returnJsonObject);
    }

    @Override
    public DataMap deleteArticle(String id) {
        //文章相关联的stuff都需要删除
        //根据id查询文章信息
        Article article = articleMapper.getArticleById(id);
        //逻辑删除，实际数据没有被删除，破坏查询条件，isDelete:0,1
        //物理删除：从数据库中删除
        articleMapper.updateLastNextId("lastArticleId",article.getLastArticleId(),article.getNextArticleId());
        articleMapper.updateLastNextId("nextArticleId",article.getLastArticleId(),article.getNextArticleId());
        return null;
    }

    @Override
    public Article getArticleById(String id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public DataMap getDraftArticle(Article article, String[] tagStr, Integer tagsSizeByName) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("articleTitle", article.getArticleTitle());
        dataMap.put("articleContent", article.getArticleContent());
        dataMap.put("articleType", article.getArticleType());
        dataMap.put("articleGrade", tagsSizeByName);
        dataMap.put("originalAuthor", article.getOriginalAuthor());
        dataMap.put("articleUrl", article.getArticleUrl());
        dataMap.put("articleCategories", article.getArticleCategories());
        dataMap.put("articleTags", tagStr);
        dataMap.put("id", article.getId());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap updateArticle(Article article) {
        Article a = articleMapper.getArticleById(article.getId().toString());
        if ("原创".equals(article.getArticleType())) {
            article.setOriginalAuthor(article.getAuthor());
            String url = "article/" + a.getArticleId();
            article.setArticleUrl(url);
        }
        articleMapper.updateArticle(article);
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("articleTitle", article.getArticleTitle());
        dataMap.put("updateDate", article.getUpdateDate());
        dataMap.put("author", article.getAuthor());
        dataMap.put("articleUrl", article.getArticleUrl());
        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap getMyArticles(int rows, int pageNum) {
        //打开分页插件
        PageHelper.startPage(pageNum, rows);
        //查询文章存入集合中
        List<Article> articles = articleMapper.getArticleManagement();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        //封装文章数据
        ArrayList<Map<String, Object>> newArticles = new ArrayList<>();
        //循环遍历集合数据
        HashMap<String, Object> map;
        for (Article article : articles) {
            map = new HashMap<>();
            map.put("thisArticleUrl", article.getArticleUrl());
            map.put("articleTitle", article.getArticleTitle());
            map.put("articleType", article.getArticleType());
            map.put("publishDate", article.getPublishDate());
            map.put("originalAuthor", article.getOriginalAuthor());
            map.put("articleCategories", article.getArticleCategories());
            map.put("articleTabloid", article.getArticleTabloid());
            map.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            map.put("likes", article.getLikes());
            newArticles.add(map);
        }
        JSONArray returnJsonArray = JSONArray.fromObject(newArticles);
        HashMap<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("pageNum", pageInfo.getPageNum());
        pageInfoMap.put("pageSize", pageInfo.getPageSize());
        pageInfoMap.put("pages", pageInfo.getPages());
        pageInfoMap.put("total", pageInfo.getTotal());
        pageInfoMap.put("isIsLastPage", pageInfo.isIsLastPage());
        pageInfoMap.put("isIsFirstPage", pageInfo.isIsFirstPage());

        returnJsonArray.add(pageInfoMap);
        return DataMap.success().setData(returnJsonArray);

    }

    @Override
    public Map<String, String> findArticleTitleByArticleId(long articleId) {
        Article article = articleMapper.findArticleTitleByArticleId(articleId);
        Map<String, String> articleMap = new HashMap<>();
        if(article != null){
            articleMap.put("articleTitle",article.getArticleTitle());
            articleMap.put("articleTabloid",article.getArticleTabloid());
        }
        return articleMap;
    }

    @Override
    public DataMap getArticleByArticleId(long articleId) {
        Article article = articleMapper.getArticleByArticleId(articleId);

        if(article != null){
            Map<String, Object> dataMap = new HashMap<>(32);
            Article lastArticle = articleMapper.getArticleByArticleId(articleId);
            Article nextArticle = articleMapper.getArticleByArticleId(articleId);
            dataMap.put("author",article.getAuthor());
            dataMap.put("articleId",articleId);
            dataMap.put("originalAuthor",article.getOriginalAuthor());
            dataMap.put("articleTitle",article.getArticleTitle());
            dataMap.put("publishDate",article.getPublishDate());
            dataMap.put("updateDate",article.getUpdateDate());
            dataMap.put("articleContent",article.getArticleContent());
            dataMap.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            dataMap.put("articleType",article.getArticleType());
            dataMap.put("articleCategories",article.getArticleCategories());
            dataMap.put("articleUrl",article.getArticleUrl());
            dataMap.put("likes",article.getLikes());

            dataMap.put("isLiked",1);

            if(lastArticle != null){
                dataMap.put("lastStatus","200");
                dataMap.put("lastArticleTitle",lastArticle.getArticleTitle());
                dataMap.put("lastArticleUrl","/article/" + lastArticle.getArticleId());
            } else {
                dataMap.put("lastStatus","500");
                dataMap.put("lastInfo","无");
            }
            if(nextArticle != null){
                dataMap.put("nextStatus","200");
                dataMap.put("nextArticleTitle",nextArticle.getArticleTitle());
                dataMap.put("nextArticleUrl","/article/" + nextArticle.getArticleId());
            } else {
                dataMap.put("nextStatus","500");
                dataMap.put("nextInfo","无");
            }
            return DataMap.success().setData(dataMap);
        }

        return DataMap.fail(CodeType.ARTICLE_NOT_EXIST);
    }
}
