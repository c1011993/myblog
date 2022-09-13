package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.service.TagService;
import com.javatiaocao.myblog.service.UserService;
import com.javatiaocao.myblog.utils.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

/**
 * @program: mybolg
 * @Title ArticleController
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 16:50
 * @Version 1.0
 **/
@Slf4j
@RestController
@Api(tags = "文章相关模块.",value = "包含几块内容")
public class ArticleController {
    @Autowired
    private TagService tagService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    /**
     * @return
     * @Author ChengYi
     * @Description //TODO 发布博客
     * @Date 17:08 2022/8/25
     * @Param
     **/
    @PostMapping(value = "/publishArticle")
    public String publishArticle(Article article, @RequestParam("articleGrade") String articleGrade,
                                 @AuthenticationPrincipal Principal principal, HttpServletRequest request) {

        try {
            //获取文章作者
//            String name = principal.getName();
            String name = "chengyi";
            //获取html代码中生成的文章摘要
            //赋值到文章对应的articleTabloid(摘要)
            BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
            String articleHtmlContent2 = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
            article.setArticleTabloid(articleHtmlContent2 + "...");

            //处理前端传递过来的标签的关键字、空格进行处理
            String[] articleTags = request.getParameterValues("articleTagsValue");
            String[] newArticleTags = new String[articleTags.length + 1];
            for (int i = 0; i < articleTags.length; i++) {
                //进行处理
                newArticleTags[i] = articleTags[i].replaceAll("<br>", StringUtil.BLANK).replaceAll("$nbsp", StringUtil.BLANK)
                        .replaceAll("\\s", StringUtil.BLANK).trim();
            }
            //获取类型是转载还是原创
            newArticleTags[articleTags.length] = article.getArticleType();
            //插入对应的实体类
            tagService.insertTags(newArticleTags, Integer.parseInt(articleGrade));
            //通过是否有文章id判断当前接口是处理新增文章还是修改文章
            //有id是修改，没有id是新增
            String id = request.getParameter("id");
            if (!StringUtil.BLANK.equals(id) && id != null) {
                //修改文章
                //todo
            }
            //新增文章
            TimeUtil timeUtil = new TimeUtil();
            String formatDateForThree = timeUtil.getFormatDateForThree();
            long articleId = timeUtil.getLongTime();
            //封装实体类
            article.setArticleId(articleId);
            article.setAuthor(name);
            article.setArticleTags("StringAnd");
            article.setPublishDate(formatDateForThree);
            article.setUpdateDate(formatDateForThree);

            DataMap data = articleService.insertArticle(article);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController publishArticle Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return
     * @Author ChengYi
     * @Description //TODO 获取文章列表接口
     * @Date 15:15 2022/8/25
     * @Param
     **/
    @PostMapping(value = "/getArticleManagement")
    public String getArticleManagement(@RequestParam("rows") int rows, @RequestParam("pageNum") int pageNum) {

        try {
            DataMap data = articleService.getArticleManagement(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController getArticleManagement Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return
     * @Author ChengYi
     * @Description //TODO  删除文章
     * @Date 17:10 2022/8/25
     * @Param
     **/
    @PostMapping(value = "/deleteArticle")
    public String deleteArticle(@RequestParam("id") String id) {

        try {
            if (StringUtil.BLANK.equals(id) || id == null) {
                return JsonResult.fail(CodeType.DELETE_ARTICLE_FAIL).toJSON();
            }

            DataMap data = articleService.deleteArticle(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController getArticleManagement Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return
     * @Author ChengYi
     * @Description //TODO 获得要编辑的文章的草稿
     * @Date 21:33 2022/8/26
     * @Param
     **/
    @GetMapping(value = "/getDraftArticle")
    public String getDraftArticle(HttpServletRequest request) {
        try {
            String id = (String) request.getSession().getAttribute("id");
            //判断文章是否是修改
            if (id != null) {
                Article article = articleService.getArticleById(id);
                int lastIndexOf = article.getArticleTags().lastIndexOf(",");
                if (lastIndexOf != -1) {
                    String[] tagStr = StringAndArray.stringToArray(article.getArticleTags().substring(0, lastIndexOf));
                    DataMap dataMap = articleService.getDraftArticle(article, tagStr, tagService.getTagsSizeByName(tagStr[0]));
                    return JsonResult.build(dataMap).toJSON();
                } else {
                    String[] tagStr = StringAndArray.stringToArray(article.getArticleTags());
                    DataMap dataMap = articleService.getDraftArticle(article, tagStr, tagService.getTagsSizeByName(tagStr[0]));
                    return JsonResult.build(dataMap).toJSON();
                }
            }
            //判断写文章是否登录超时
            if (request.getSession().getAttribute("article") != null) {
                Article article = (Article) request.getSession().getAttribute("article");
                String[] tagStr = (String[]) request.getSession().getAttribute("articleTags");
                String articleGrade = (String) request.getSession().getAttribute("articleGrade");
                if (!StringUtil.BLANK.equals(articleGrade) && articleGrade != null) {
                    DataMap dataMap = articleService.getDraftArticle(article, tagStr, Integer.parseInt(articleGrade));
                    request.getSession().removeAttribute("article");
                    request.getSession().removeAttribute("tagStr");
                    request.getSession().removeAttribute("articleGrade");
                    return JsonResult.build(dataMap).toJSON();
                }
            }
            return JsonResult.fail().toJSON();
        } catch (Exception e) {
            log.error("ArticleController getDraftArticle Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return
     * @Author ChengYi
     * @Description //
     * @Date 16:56 2022/8/28
     * @Param
     **/
    @GetMapping(value = "/canYouWrite")
    public String canYouWrite(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (!Objects.isNull(userPrincipal)) {
                String username = userPrincipal.getName();
                User user = userService.userNameIsExist(username);
                if (!Objects.isNull(user)) {
                    return JsonResult.success().toJSON();
                }
            }
            return JsonResult.fail().toJSON();
        } catch (Exception e) {
            log.error("ArticleController canYouWrite Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
    /** @Author ChengYi
     * @Description //获取文章分页列表
     * @Date 22:34 2022/8/28
     * @Param
     * @return
     **/
    @PostMapping(value = "/myArticles")
    public String getMyArticles(@RequestParam("rows") int rows, @RequestParam("pageNum") int pageNum) {
        try {
            DataMap data = articleService.getMyArticles(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController getMyArticles Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /** @Author ChengYi
     * @Description //获取文章
     * @Date 22:34 2022/8/28
     * @Param
     * @return
     **/
    @PostMapping(value = "/getArticleByArticleId")
    public String getArticleByArticleId(@RequestParam("articleId") String articleId,HttpServletRequest request) {
        try {
            DataMap data = articleService.getArticleByArticleId(Long.parseLong(articleId));
            return JsonResult.build(data).toJSON();
        } catch (Exception e){
            log.error("[{}] get article [{}] exception",articleId, e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

}
