package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.service.CategoriesService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xuancheng Huang
 * @version 1.0
 */
@Api(tags = "目录相关模块")
@Slf4j
@RestController
//等价于@Controller + @ResponseBody
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    /**
     * 查询分类列表接口
     *
     * @return
     */
    @GetMapping(value = "/getArticleCategories")
    //等价于@RequestMapping(value="/getArticleCategories",method=RequestMethod.GET)
    public String getArticleCategories() {
        try {
            //
            DataMap data = categoriesService.getArticleCategories();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController getCategories Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 根据分类名称，新增或删除分类
     *
     * @param categoryName
     * @param type
     * @return
     */
    @Transactional
    @PostMapping(value = "/updateCategory")
    public String updateCategory(@RequestParam(value = "categoryName") String categoryName, @RequestParam(value = "type") int type) {
        log.info(categoryName,type);
        try {
            //开始增加分类(存储到数据库中)
            DataMap data = categoriesService.updateCategory(categoryName, type);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoryController addCategory Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    /**
     * 查询分类下拉接口
     *
     * @return
     */
    @GetMapping(value = "/findCategoriesName")
    public String findCategoriesName() {
        try {
            //
            DataMap data = categoriesService.findCategoriesName();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController findCategories Exception", e);
        }
        //失败
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
