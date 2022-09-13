package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
@Slf4j
public class Test {

    @Autowired
    private UserService userService;

    //去localhost:8080/hi的页面去获取资源，结果没有获取到，然后返回hi给前端页面
    @GetMapping(value = "/hi")
    public String hi() {
        User userByPhone = userService.findUserByPhone("188888888");
        return userByPhone.toString();

    }
}
