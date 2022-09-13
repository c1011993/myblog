package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: mybolg
 * @Title Privateword
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-29 23:21
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateWord {
    private String id;
    private String privateWord;
    private String publisherId;
    private String replierId;
    private String replyContent;
    private String publisherDate;
}
