package com.javatiaocao.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.FeedbackMapper;
import com.javatiaocao.myblog.model.Feedback;
import com.javatiaocao.myblog.model.PrivateWord;
import com.javatiaocao.myblog.service.FeedbackService;
import com.javatiaocao.myblog.service.UserService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mybolg
 * @Title FeedbackServiceImpl
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-29 17:55
 * @Version 1.0
 **/
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackMapper feedbackMapper;
    @Autowired
    UserService userService;

    @Override
    public DataMap getAllFeedback(int rows, int pageNum) {
        //打开分页插件
        PageHelper.startPage(pageNum, rows);
        //查询的反馈存在集合中
        List<Feedback> feedbackList = feedbackMapper.getAllFeedback();
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbackList);
        //返回处理数据
        JSONArray returnJsonArray = new JSONArray();
        JSONObject returnJsonObject = new JSONObject();

        JSONObject feedbackJson;
        for (Feedback feedback : feedbackList) {
            feedbackJson = new JSONObject();
            feedbackJson.put("person", userService.findUsernameById(feedback.getPersonId()));
            feedbackJson.put("feedbackDate", feedback.getFeedbackDate());
            if (feedback.getFeedbackContent() == null) {
                feedbackJson.put("contactInfo", StringUtil.BLANK);
            } else {
                feedbackJson.put("contactInfo", feedback.getContactInfo());
            }
            feedbackJson.put("contactInfo", feedback.getContactInfo());
            returnJsonArray.add(feedbackJson);
        }
        returnJsonObject.put("result", returnJsonArray);
        //处理分页页面显示内容
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());

        returnJsonObject.put("pageInfo", pageJson);
        return DataMap.success().setData(returnJsonObject);
    }

    @Override
    public DataMap getAllPrivateWord() {
        List<PrivateWord> privateWordList = feedbackMapper.getAllPrivateWord();

        JSONObject returnJson = new JSONObject();
        JSONArray returnArray = new JSONArray();
        JSONObject userJson;

        ArrayList<String> publishers = new ArrayList<>();
        String publisher;
        for (PrivateWord privateWord : privateWordList) {
            userJson = new JSONObject();
            userJson.put("privateWord", privateWord.getPrivateWord());
            publisher = userService.findUsernameById(Integer.parseInt(privateWord.getPublisherId()));
            userJson.put("publisher", publisher);
            userJson.put("publisherDate", privateWord.getPublisherDate());
            userJson.put("id", privateWord.getId());
            if (privateWord.getReplyContent() == null) {
                userJson.put("replier", StringUtil.BLANK);
                userJson.put("replyContent", StringUtil.BLANK);
            } else {
                userJson.put("replier", userService.findUsernameById(Integer.parseInt(privateWord.getPublisherId())));
                        userJson.put("replyContent", privateWord.getReplyContent());
            }
            if (!publishers.contains(publisher)) {
                publishers.add(publisher);
                JSONObject jsonObject2 = new JSONObject();
                JSONArray jsonArray2 = new JSONArray();
                jsonObject2.put("publisher", publisher);
                jsonObject2.put("content", jsonArray2);
                returnArray.add(jsonObject2);
            }
            for (int i = 0; i < returnArray.size(); i++) {
                JSONObject arrayUser = (JSONObject) returnArray.get(i);
                if (arrayUser.get("publisher").equals(publisher)) {
                    JSONArray jsonArray = (JSONArray) arrayUser.get("content");
                    jsonArray.add(userJson);
                    arrayUser.put("publisher", publisher);
                    arrayUser.put("content", jsonArray);
                    returnArray.remove(i);

                    returnArray.add(arrayUser);
                    break;
                }
            }
        }
        returnJson.put("result", returnArray);
        return DataMap.success().setData(returnJson);
    }
}
