package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.model.Role;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Service//controller层没有注入这个，所以也可以不加
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String phone) {
        //查询用户信息
        User user = userMapper.findUserByPhone(phone);
        //判断是否为空
        /**
         * name是枚举中的方法, 返回枚举常量的名称
         */
        if (Objects.isNull(user)){
            throw new UsernameNotFoundException(CodeType.USERNAME_NOT_EXIST.name());
        }
        //当用户不为空时，查询用户角色
        List<Role> roles = userMapper.queryRolesByPhone(phone);
        user.setRoles(roles);

        //处理记录用户登录系统的时间
        TimeUtil timeUtil = new TimeUtil();
        String recentlyLanded = timeUtil.getFormatDateForSix();
        //更新用户登录时间
        userMapper.updateRecentlyLanded(phone,recentlyLanded);
        //添加权限
        ArrayList<SimpleGrantedAuthority> auths = new ArrayList<>();
        for (Role role : user.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),auths);
    }
}
