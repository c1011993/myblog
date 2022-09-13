package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Categories;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Xuancheng Huang
 * @version 1.0
 */
public interface UserService {
    //通过手机号码查询用户
    User findUserByPhone(String phone);

    User userNameIsExist(String username);

    @Transactional
    DataMap insertUser(User user);

    DataMap getUserPersonalInfo(String username);

    String findUsernameById(Integer id);

//    User phoneNumberIsExist(String phone);

}
