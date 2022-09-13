package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Categories;
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
public interface CategoriesMapper {
    List<Categories> getArticleCategories();

    int findIsExistByCategoryName(@Param(value = "categoryName") String categoryName);

    void updateCategory(Categories categories);

    void deleteCategory(String categoryName);


    List<String> findCategoriesName();
}
