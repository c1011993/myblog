package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

/**
 * @program: mybolg
 * @Title FeedBack
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-29 17:43
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    private Integer id;
    private String feedbackContent;
    private String contactInfo;
    private Integer personId;
    private String feedbackDate;
}
