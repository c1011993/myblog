package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categories {
    private Integer id;
    private String categoryName;
}
