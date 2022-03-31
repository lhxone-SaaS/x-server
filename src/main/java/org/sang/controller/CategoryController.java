package org.sang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sang.pojo.Category;
import org.sang.pojo.RespBean;
import org.sang.constants.ResponseStateConstant;
import org.sang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin/category")
@Api(value = "categoryController", tags = "目录相关接口")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "getAllCategories", notes = "获取所有目录",
            produces = "application/json", consumes = "application/json", response = List.class)
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "deleteById", notes = "根据文章ID批量删除文章",
            produces = "application/json", consumes = "application/json", response = RespBean.class)
    public RespBean deleteById(@PathVariable String ids) {
        boolean result = categoryService.deleteCategoryByIds(ids);
        if (result) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "删除成功!");
        }
        return new RespBean(ResponseStateConstant.SERVER_ERROR, "删除失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "addNewCate", notes = "添加目录",
            produces = "application/json", consumes = "application/json", response = RespBean.class)
    public RespBean addNewCate(@RequestBody Category category) {

        if ("".equals(category.getCateName()) || category.getCateName() == null) {
            return new RespBean(ResponseStateConstant.PARAM_ERROR, "请输入栏目名称!");
        }

        int result = categoryService.addCategory(category);

        if (result == 1) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "添加成功!");
        }
        return new RespBean(ResponseStateConstant.SERVER_ERROR, "添加失败!");
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RespBean updateCate(Category category) {
        int i = categoryService.updateCategoryById(category);
        if (i == 1) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "修改成功!");
        }
        return new RespBean(ResponseStateConstant.SERVER_ERROR, "修改失败!");
    }
}
