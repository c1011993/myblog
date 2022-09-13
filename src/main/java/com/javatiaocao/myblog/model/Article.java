package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {


/*    private Long articleId;
    private String articleTitle;
    private String articleContent;
    private String articleType;
    private String articleCategories;
    private String originalAuthor;
    private String articleUrl;


    private Integer id;
    private String author;
    private String articleTags;
    private String publishDate;
    private String updateDate;
    private String articleTabloid;
    private Integer likes;
    private Long lastArticleId;
    private Long nextArticleId;*/


//<tr id="a' + obj['id'] + '"><td><a href="article/' + obj['articleId'] + '">' + obj['articleTitle'] + '</a></td><td>' +
    // obj['publishDate'] + '</td><td>' + obj['articleCategories'] + '</td> <td><span class="am-badge am-badge-success">' +
    // obj['visitorNum'] + '</span></td>' +'<td>' +


  /*  private Integer id;
    private Long articleId;
    private String articleTitle;
    private String publishDate;
    private String articleCategories;
    visitorNum
    */



    private Integer id;
    private Long articleId;
    private String author;
    private String originalAuthor;
    private String articleTitle;
    private String articleContent;
    private String articleTags;
    private String articleType;
    private String articleCategories;
    private String publishDate;
    private String updateDate;
    private String articleUrl;
    private String articleTabloid;
    private Integer likes;
    private Long lastArticleId;
    private Long nextArticleId;

}
