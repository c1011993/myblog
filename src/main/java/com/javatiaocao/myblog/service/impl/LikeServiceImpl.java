package com.javatiaocao.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.LikesMapper;
import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.model.FriendLink;
import com.javatiaocao.myblog.service.LikesService;
import com.javatiaocao.myblog.utils.DataMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: mybolg
 * @Title LikeServiceImpl
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-28 23:26
 * @Version 1.0
 **/
@Service
public class LikeServiceImpl implements LikesService {
    @Autowired
    private LikesMapper likesMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public DataMap getArticleThumbsUp(int rows, int pageNum) {
        //打开分页插件
        PageHelper.startPage(pageNum, rows);
        //查询文章存入集合中
        List<ArticleLikesRecord> likesRecords = likesMapper.getArticleThumbsUp();
        PageInfo<ArticleLikesRecord> pageInfo = new PageInfo<>(likesRecords);

        //封装文章数据
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for (ArticleLikesRecord likesRecord : likesRecords) {
            jsonObject = new JSONObject();
            jsonObject.put("id", likesRecord.getId());
            jsonObject.put("articleId", likesRecord.getArticleId());
            jsonObject.put("articleTitle", articleMapper.getArticleTitleByArticleId(likesRecord.getArticleId()));
            jsonObject.put("likeDate", likesRecord.getLikeDate());
            jsonObject.put("praisePeople", articleMapper.getArticleAuthorByArticleId(likesRecord.getArticleId()));
            jsonObject.put("isRead", likesRecord.getIsRead());
            jsonArray.add(jsonObject);
        }
        returnJson.put("result", jsonArray);
        returnJson.put("msgIsNotReadNum", likesMapper.getMsgIsNotReadNum());
        //封装最外层数据
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());

        returnJson.put("pageInfo", pageJson);
        return DataMap.success().setData(returnJson);


    }

    @Override
    public DataMap readAllThumbsUp() {
        likesMapper.readAllThumbsUp();
        return DataMap.success();
    }

    @Override
    public DataMap getFriendLink() {
        FriendLink[] friendLinks = likesMapper.getFriendLink();
        return DataMap.success().setData(friendLinks);

    }

    @Override
    @Transactional
    public DataMap deleteFriendLink(String id) {
        likesMapper.deleteFriendLink(id);
        return DataMap.success(CodeType.DELETE_FRIEND_LINK_SUCCESS);
    }

    @Override
    @Transactional
    public DataMap addFriendLink(String blogger,String url) {
        likesMapper.addFriendLink(blogger,url);
        return DataMap.success(CodeType.ADD_FRIEND_LINK_SUCCESS);
    }

    @Override
    @Transactional
    public DataMap updateFriendLink(String blogger,String url, String id) {
        likesMapper.updateFriendLink(blogger,url, id);
        return DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS);
    }
}
