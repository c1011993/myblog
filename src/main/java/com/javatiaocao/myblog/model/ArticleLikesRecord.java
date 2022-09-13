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
public class ArticleLikesRecord {
    private Integer id;
    private Long articleId;
    private Integer likerId;
    private String likeDate;
    private Boolean isRead;
}
