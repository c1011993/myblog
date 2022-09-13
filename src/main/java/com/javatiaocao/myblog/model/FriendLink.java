package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: mybolg
 * @Title FriendLink
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-29 15:29
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLink {
    private Integer id;
    private String blogger;
    private String url;

    public FriendLink(String blogger, String url) {
        this.blogger = blogger;
        this.url = url;
    }
}
