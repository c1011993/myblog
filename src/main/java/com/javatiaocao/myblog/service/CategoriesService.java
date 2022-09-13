package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.stereotype.Service;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */

public interface CategoriesService {

    DataMap getArticleCategories();

    DataMap updateCategory(String categoryName,int type);

    DataMap findCategoriesName();
}
