package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.LikesService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: mybolg
 * @Title LikesController
 * @description: 点赞管理
 * @author: ChengYi
 * @date: 2022-08-28 23:21
 * @Version 1.0
 **/
@Slf4j
@RestController
public class LikesController {
    @Autowired
    private LikesService likesService;

    @PostMapping(value = "/getArticleThumbsUp")
    public String getArticleManagement(@RequestParam("rows") int rows, @RequestParam("pageNum") int pageNum) {
        try {
            DataMap data = likesService.getArticleThumbsUp(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController getArticleThumbsUp Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return
     * @Author ChengYi
     * @Description //TODO 标记为已读窗口
     * @Date 15:14 2022/8/29
     * @Param
     **/
    @GetMapping(value = "/readAllThumbsUp")
    public String readAllThumbsUp() {
        try {
            DataMap data = likesService.readAllThumbsUp();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController readAllThumbsUp Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return
     * @Author ChengYi
     * @Description //TODO获得友情连接
     * @Date 15:31 2022/8/29
     * @Param
     **/
    @PostMapping(value = "/getFriendLink")
    public String getFriendLink() {
        try {
            DataMap data = likesService.getFriendLink();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController getFriendLink Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * @return
     * @Author ChengYi
     * @Description //TODO更新友情连接
     * @Date 15:31 2022/8/29
     * @Param
     **/
    @PostMapping(value = "/updateFriendLink")
    public String addOrUpdateFriendLink(@RequestParam("id") String id, @RequestParam("blogger") String blogger, @RequestParam("url") String url) {
        try {
            DataMap data;
            if (StringUtil.BLANK.equals(id)){
                //新增友链
                data = likesService.addFriendLink(blogger,url);
            }else {
                //编辑友链
               data = likesService.updateFriendLink(blogger,url,id);
            }
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("LikesController updateFriendLink Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    /**
     * @return
     * @Author ChengYi
     * @Description //TODO 在友情链接中删除
     * @Date 15:31 2022/8/29
     * @Param
     **/
    @PostMapping(value = "/deleteFriendLink")
    public String deleteFriendLink(@RequestParam("id") String id) {
        try {
           DataMap date = likesService.deleteFriendLink(id);
            return JsonResult.build(date).toJSON();
        } catch (Exception e) {
            log.error("LikesController deleteFriendLink Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
