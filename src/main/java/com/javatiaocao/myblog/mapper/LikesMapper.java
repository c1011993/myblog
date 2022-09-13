package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.model.FriendLink;
import com.javatiaocao.myblog.utils.DataMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybolg
 * @Title LikesMapper
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-28 23:31
 * @Version 1.0
 **/
@Mapper
@Repository
public interface LikesMapper {
    List<ArticleLikesRecord> getArticleThumbsUp();

    Integer getMsgIsNotReadNum();

    void readAllThumbsUp();

    FriendLink[] getFriendLink();

    void deleteFriendLink(String id);

    void addFriendLink(@Param("blogger") String blogger, @Param("url") String url);

    void updateFriendLink(@Param("blogger") String blogger,@Param("url")String url,@Param("id") String id);
}

