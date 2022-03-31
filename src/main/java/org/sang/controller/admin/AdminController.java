package org.sang.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sang.pojo.Article;
import org.sang.pojo.RespBean;
import org.sang.constants.ResponseStateConstant;
import org.sang.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin")
@Api(value = "adminController", tags = "管理员相关接口")
public class AdminController {
    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/article/all", method = RequestMethod.GET)
    @ApiOperation(value = "getArticlesByState", notes = "通过状态查询文章列列表",
            produces = "application/json", consumes = "application/json", response = RespBean.class)
    public RespBean<Map<String, Object>> getArticlesAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
         @RequestParam(value = "count", defaultValue = "6") Integer count,
         String keywords) {
        List<Article> articles = articleService.getArticleByState(-2, page, count, keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("articles", articles);
        map.put("totalCount", articleService.getArticleCountByState(1, null, keywords));
        RespBean<Map<String, Object>> respBean = new RespBean<>(200, "ok");
        respBean.setData(map);
        return respBean;
    }

    @RequestMapping(value = "/article/dustbin", method = RequestMethod.PUT)
    @ApiOperation(value = "updateArticleState", notes = "批量更新文章状态",
            produces = "application/json", consumes = "application/json", response = RespBean.class)
    public RespBean updateArticleState(@RequestBody Long[] aids, @RequestParam("state") Integer state) {
        if (articleService.updateArticleState(aids, state) == aids.length) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "删除成功!");
        }
        return new RespBean(ResponseStateConstant.SERVER_ERROR, "删除失败!");
    }
}
