package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: mybolg
 * @Title Tag
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 21:55
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private int id;
    private String tagName;
    private int tagSize;

    public Tag(String tagName, int tagSize) {
        this.tagName = tagName;
        this.tagSize = tagSize;
    }
}
