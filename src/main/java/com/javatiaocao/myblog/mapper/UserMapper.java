package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Role;
import com.javatiaocao.myblog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Mapper
@Repository
public interface UserMapper {
    User findUserByPhone(@Param(value = "phone") String phone);

    User userNameIsExist(@Param(value = "username") String username);

    void insertUser(User user);

//    User phoneNumberIsExist(@Param(value = "phone") String phone);

    void updateRecentlyLanded(@Param(value = "phone") String phone, @Param(value = "recentlyLanded") String recentlyLanded);

    List<Role> queryRolesByPhone(String role);

    void updateRoleByUserId(@Param(value = "userId") int userId,@Param(value = "roleId") int roleId);

    User getUserPersonalInfo(String username);

    String findUsernameById(Integer id);
}
