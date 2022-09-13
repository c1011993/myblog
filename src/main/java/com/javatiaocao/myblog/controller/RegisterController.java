package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.UserService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

/**
 * @author Xuancheng Huang
 * @version 1.0
 * 注册类控制层
 */
@Slf4j
@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public String register(User user, HttpServletRequest request) {

        try {
            /**
             * 处理控制层数据
             */
            //判断手机号是否已注册
            if (userService.findUserByPhone(user.getPhone()) != null) {
                return JsonResult.fail(CodeType.PHONE_EXIST).toJSON();
            }

            //判断用户名是否存在
            User userNameIsExist = userService.userNameIsExist(user.getUsername());
            if (userNameIsExist != null) {
                return JsonResult.fail(CodeType.USERNAME_EXIST).toJSON();
            }
            //对密码加密
            MD5Util md5Util = new MD5Util();
            user.setPassword(md5Util.encode(user.getPassword()));

            //开始注册(存储到数据库中)
            DataMap data = userService.insertUser(user);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("RegisterController register Exception", user, e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/getUserPersonalInfo")
    public String getUserPersonalInfo(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {

        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (!Objects.isNull(userPrincipal)) {
                String username = userPrincipal.getName();
                DataMap data = userService.getUserPersonalInfo(username);
                return JsonResult.build(data).toJSON();
            }
        } catch (Exception e) {
            log.error("RegisterController getUserPersonalInfo Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
