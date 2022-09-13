package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: mybolg
 * @Title Comment
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-09-02 11:35
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRecord {
    private Long id;
    private Long pId;
    private Long articleId;
    private Integer answererId;
    private Integer respondentId;
    private String commentDate;
    private Integer likes;
    private String commentContent;
    private Boolean isRead;
}
