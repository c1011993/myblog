package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.mapper.TagMapper;
import com.javatiaocao.myblog.model.Tag;
import com.javatiaocao.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: mybolg
 * @Title TagServiceImpl
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 21:52
 * @Version 1.0
 **/
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public void insertTags(String[] newArticleTags, int tagSize) {
        for (String newArticleTag : newArticleTags) {
            //插入之前进行判断，去重标签
            if (!tagMapper.findIsExistByTagName(newArticleTag)){
                Tag tag = new Tag(newArticleTag, tagSize);
                tagMapper.saveTags(tag);
            }
        }
    }

    @Override
    public Integer getTagsSizeByName(String tagName) {
       return tagMapper.getTagsSizeByName(tagName);
    }
}
