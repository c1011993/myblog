package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.mapper.StatisticsMapper;
import com.javatiaocao.myblog.service.StatisticsService;
import com.javatiaocao.myblog.utils.DataMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @program: mybolg
 * @Title StatisticsServiceImpl
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-30 10:05
 * @Version 1.0
 **/
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    StatisticsMapper statisticsMapper;

    @Override
    public DataMap getStatisticsInfo() {
//       ArrayList<Integer> statisticsList = statisticsMapper.getStatisticsInfo();
        int allVisitor = statisticsMapper.getAllVisitor();
        int yesterdayVisitor = statisticsMapper.getYesterdayVisitor();
        int allUser = statisticsMapper.getAllUser();
        int articleNum = statisticsMapper.getArticleNum();
        int articleThumbsUpNum = statisticsMapper.getArticleThumbsUpNum();
        if (articleThumbsUpNum != 0) {
            articleThumbsUpNum = statisticsMapper.getArticleThumbsUpNum();
        } else {
            articleThumbsUpNum = 0;
        }

        JSONObject returnJson = new JSONObject();

        returnJson.put("allVisitor", allVisitor);
        returnJson.put("yesterdayVisitor", yesterdayVisitor);
        returnJson.put("allUser", allUser);
        returnJson.put("articleNum", articleNum);
        returnJson.put("articleThumbsUpNum", articleThumbsUpNum);

        return DataMap.success().setData(returnJson);

    }
}
