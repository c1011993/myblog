package com.javatiaocao.myblog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.CategoriesMapper;
import com.javatiaocao.myblog.model.Categories;
import com.javatiaocao.myblog.service.CategoriesService;
import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    CategoriesMapper categoriesMapper;

    @Override
    //查找的category的对象，要求以Categories对象的形式用JSON格式返回
    public DataMap getArticleCategories() {
        //查询分类数据
        List<Categories> categories = categoriesMapper.getArticleCategories();
        //处理查询出的数据
        JSONObject returnJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Categories category : categories) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", category.getId());
            jsonObject.put("categoryName", category.getCategoryName());
            //将封装好的jsonObject放入数组
            jsonArray.add(jsonObject);
        }
        //JS操作的是JSON对象，所以要把JSONArray再次放入JSONObject中作为一个对象
        returnJson.put("result", jsonArray);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap updateCategory(String categoryName, int type) {
        //判断目录分类是否已存在
        int isExistCategoryName = categoriesMapper.findIsExistByCategoryName(categoryName);
        //判断是新增or删除，1是增，2是删，这个数字是跟前端开发约定的，比如当用户点击添加按钮时，触发监听函数设置type=1
        if (type == 1) {
            if (isExistCategoryName == 0) {
                //说明没查到分类,可以进行新增
                Categories categories = new Categories();
                categories.setCategoryName(categoryName);
                categoriesMapper.updateCategory(categories);
                int newCategoriesId = categoriesMapper.findIsExistByCategoryName(categoryName);
                return DataMap.success(CodeType.ADD_CATEGORY_SUCCESS).setData(newCategoriesId);
            }
        } else {
            //删除操作
            if (isExistCategoryName != 0) {
                //TODO 查询分类下面对应多少文章
                //如果文章数量不为空，返回提示，删除失败
                categoriesMapper.deleteCategory(categoryName);
                return DataMap.success(CodeType.DELETE_CATEGORY_SUCCESS);
            }
            return DataMap.fail(CodeType.CATEGORY_NOT_EXIST);
        }
        return DataMap.fail(CodeType.CATEGORY_EXIST);
    }

    @Override
    //查找的是category的name，要求以String的形式用JSON格式返回
    public DataMap findCategoriesName() {
       List<String> categories = categoriesMapper.findCategoriesName();
       return DataMap.success().setData(categories);
    }
}
