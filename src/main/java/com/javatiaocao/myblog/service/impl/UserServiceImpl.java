package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.model.Categories;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.UserService;
import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;


/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByPhone(String phone) {
        return userMapper.findUserByPhone(phone);
    }

    @Override
    public User userNameIsExist(String username) {
        User user = userMapper.userNameIsExist(username);
        return user;
    }

    @Override
    public DataMap insertUser(User user) {

        //判断用户名是否异常
        if (user.getUsername().length() > 35) {
            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
        }
        String username = user.getUsername();
        username = user.getUsername().trim();

        /*//判断手机号码是否存在
        User userIsExist = userMapper.phoneIsExist(user.getPhone());
        if (userIsExist != null) {
            return DataMap.fail(CodeType.PHONE_EXIST);
        }*/
        //设置默认头像
        if ("male".equals(user.getGender())) {
            user.setAvatarImgUrl("www.javatiaocao.com");
        } else {
            user.setAvatarImgUrl("www.javatiaocao.com");
        }
        //设置trueName
        user.setTrueName("www.javatiao.com");

        userMapper.insertUser(user);
        //注册成功的时候，配备角色,默认普通用户
        User userByPhone = userMapper.findUserByPhone(user.getPhone());
        updateRoleByUserId(userByPhone.getId(), 1);
        return DataMap.success();
    }

    @Override
    public DataMap getUserPersonalInfo(String username) {
        User user = userMapper.getUserPersonalInfo(username);
        return DataMap.success().setData(user);
    }

    @Override
    public String findUsernameById(Integer id) {
        return userMapper.findUsernameById(id);
    }

    private void updateRoleByUserId(int id, int roleId) {
        userMapper.updateRoleByUserId(id, roleId);
    }

}
