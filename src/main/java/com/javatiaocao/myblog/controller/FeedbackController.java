package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.FeedbackService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: mybolg
 * @Title FeedbackController
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-29 17:47
 * @Version 1.0
 **/
@Api(tags = "反馈相关模块")
@Slf4j
@RestController
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @GetMapping("/getAllFeedback")
    public String getAllFeedback(@RequestParam("rows") int rows, @RequestParam("pageNum") int pageNum) {
        try {
            DataMap data = feedbackService.getAllFeedback(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController getAllFeedback Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @return
     * @Author ChengYi
     * @Description //悄悄话
     * @Date 23:12 2022/8/29
     * @Param
     **/
    @PostMapping("/getAllPrivateWord")
    public String getAllPrivateWord() {
        try {
            DataMap data = feedbackService.getAllPrivateWord();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController getAllPrivateWord Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
