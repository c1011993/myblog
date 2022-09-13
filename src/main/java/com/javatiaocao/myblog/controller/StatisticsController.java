package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.StatisticsService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mybolg
 * @Title StatisticsController
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-30 10:02
 * @Version 1.0
 **/
@Slf4j
@RestController
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @GetMapping(value = "/getStatisticsInfo")
    public String getStatisticsInfo() {
        try {
            DataMap data = statisticsService.getStatisticsInfo();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("StatisticsController getStatisticsInfo Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
