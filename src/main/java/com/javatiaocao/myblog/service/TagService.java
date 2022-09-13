package com.javatiaocao.myblog.service;

/**
 * @program: mybolg
 * @Title TagService
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 21:50
 * @Version 1.0
 **/
public interface TagService {

    void insertTags(String[] newArticleTags, int parseInt);

    Integer getTagsSizeByName(String tagName);
}
