package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String phone;
    private String username;
    private String password;
    private String gender;
    private String trueName;
    private String birthday;
    private String email;
    private String personalBrief;
    private String avatarImgUrl;
    private String recentlyLanded;

    private List<Role> roles;
}
