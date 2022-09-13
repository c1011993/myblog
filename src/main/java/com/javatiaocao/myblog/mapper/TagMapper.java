package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @program: mybolg
 * @Title tagMapper
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 21:59
 * @Version 1.0
 **/
@Mapper
@Repository
public interface TagMapper {
    void saveTags(Tag tags);

    boolean findIsExistByTagName(String newArticleTag);

    Integer getTagsSizeByName(String tagName);
}
