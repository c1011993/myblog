package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: mybolg
 * @Title Tag
 * @description: TODO
 * @author: ChengYi
 * @date: 2022-08-23 21:55
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {
    private Integer id;
    private String visitorNum;
    private String pageName;
    /**
     * 思路
     * 每个article都应该有一个Visitor属性
     * 当点击该文章时触发一个计数函数，记录登录者的id并统计全部visitor的数量
     */
}
