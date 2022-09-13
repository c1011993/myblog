package com.javatiaocao.myblog.service;
import com.javatiaocao.myblog.utils.DataMap;

/**
 * @program: mybolg
 * @Title LikeService
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-28 23:25
 * @Version 1.0
 **/
public interface LikesService {
    DataMap getArticleThumbsUp(int rows, int pageNum);

    DataMap readAllThumbsUp();

    DataMap getFriendLink();

    DataMap deleteFriendLink(String id);

    DataMap addFriendLink(String blogger,String url);

    DataMap updateFriendLink(String blogger,String url,String id);
}
