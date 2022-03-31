package org.sang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.sang.pojo.Article;
import org.sang.pojo.RespBean;
import org.sang.constants.ResponseStateConstant;
import org.sang.service.ArticleService;
import org.sang.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sang on 2017/12/20.
 */
@RestController
@RequestMapping("/article")
@Api(value = "articleController", tags = "文章相关接口")
public class ArticleController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private static final Logger logger = LoggerFactory.getLogger(Logger.class);

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "addNewArticle", notes = "添加文章",
            consumes="application/json", produces = "application/json", response = RespBean.class)
    public RespBean<Integer> addNewArticle(@RequestBody Article article) {
        int result = articleService.addNewArticle(article);
        RespBean<Integer> respBean;
        if (result == 1) {
            respBean = new RespBean<>(ResponseStateConstant.SERVER_SUCCESS, article.getId() + "");
        } else {
            respBean = new RespBean(ResponseStateConstant.SERVER_ERROR, article.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
        respBean.setData(result);
        return respBean;
    }

    /**
     * 上传图片
     *
     * @return 返回值为图片的地址
     */
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    @ApiOperation(value = "uploadImg", notes = "上传图片", consumes="application/json",
            produces = "application/json", response = RespBean.class)
    public RespBean<String> uploadImg(HttpServletRequest req, MultipartFile image) {
        RespBean<String> respBean;
        StringBuffer url = new StringBuffer();
        String filePath = "/blogimg/" + sdf.format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            respBean = new RespBean<>(ResponseStateConstant.SERVER_SUCCESS, "上传成功");
            respBean.setData(url.toString());
        } catch (IOException e) {
            logger.error("upload error", e);
            respBean = new RespBean<>(ResponseStateConstant.SERVER_ERROR, "上传失败");
        }
        return respBean;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "getArticleByState", notes = "查询文章列表", consumes="application/json",
            produces = "application/json", response = Map.class)
    public RespBean<Map<String, Object>> getArticleByState(@RequestParam(value = "state", defaultValue = "-1") Integer state,
                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "count", defaultValue = "6") Integer count,
                                                 @RequestParam(value = "keywords", defaultValue = "") String keywords) {
        int totalCount = articleService.getArticleCountByState(state, Util.getCurrentUser().getId(),keywords);
        List<Article> articles = articleService.getArticleByState(state, page, count,keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("articles", articles);
        RespBean<Map<String, Object>> respBean = new RespBean<>(200, "ok");
        respBean.setData(map);
        return respBean;
    }

    @RequestMapping(value = "/{aid}", method = RequestMethod.GET)
    @ApiOperation(value = "getArticleById", notes = "根据文章ID查询文章详情", consumes="application/json",
            produces = "application/json", response = Article.class)
    public Article getArticleById(@PathVariable Long aid) {
        return articleService.getArticleById(aid);
    }

    @RequestMapping(value = "/dustbin", method = RequestMethod.PUT)
    @ApiOperation(value = "updateArticleState", notes = "更新文章状态", consumes="application/json",
            produces = "application/json", response = RespBean.class)
    public RespBean updateArticleState(@RequestBody Long[] aids, @RequestParam("state") Integer state) {
        if (articleService.updateArticleState(aids, state) == aids.length) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "删除成功!");
        }
        return new RespBean(ResponseStateConstant.SERVER_ERROR, "删除失败!");
    }

    @RequestMapping(value = "/restore", method = RequestMethod.PUT)
    @ApiOperation(value = "restoreArticle", notes = "还原文章", consumes="application/json",
            produces = "application/json", response = RespBean.class)
    public RespBean restoreArticle(Integer articleId) {
        if (articleService.restoreArticle(articleId) == 1) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "还原成功!");
        }
        return new RespBean(ResponseStateConstant.SERVER_ERROR, "还原失败!");
    }

    @RequestMapping(value = "/dataStatistics", method = RequestMethod.GET)
    @ApiOperation(value = "dataStatistics", notes = "统计文章数据", consumes="application/json",
            produces = "application/json", response = RespBean.class)
    public RespBean<Map<String,Object>> dataStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<String> categories = articleService.getCategories();
        List<Integer> dataStatistics = articleService.getDataStatistics();
        map.put("categories", categories);
        RespBean<Map<String,Object>> respBean = new RespBean<>(200, "ok");
        map.put("ds", dataStatistics);
        respBean.setData(map);
        return respBean;
    }



}
