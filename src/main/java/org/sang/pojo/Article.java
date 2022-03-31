package org.sang.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sang on 2017/12/20.
 */
@ApiModel
public class Article {
    @ApiModelProperty(name = "id", value = "文章ID")
    private Long id;

    @ApiModelProperty(name = "title", value="标题")
    private String title;

    @ApiModelProperty(name = "mdContent", value="文章md格式内容")
    private String mdContent;

    @ApiModelProperty(name = "htmlContent", value="文章html格式内容")
    private String htmlContent;

    @ApiModelProperty(name = "summary", value = "summary")
    private String summary;

    @ApiModelProperty(name="cid", value = "cid")
    private Long cid;

    @ApiModelProperty(name="uid", value = "uid")
    private Long uid;

    @ApiModelProperty(name="publishDate", value = "发布日期")
    private Timestamp publishDate;

    @ApiModelProperty(name="state", value = "状态")
    private Integer state;

    @ApiModelProperty(name="pageView", value = "pageView")
    private Integer pageView;

    @ApiModelProperty(name="editTime", value = "结束时间")
    private Timestamp editTime;

    @ApiModelProperty(name="dynamicTags", value = "动态标签数组")
    private String[] dynamicTags;

    @ApiModelProperty(name="nickname", value = "昵称")
    private String nickname;

    @ApiModelProperty(name="cateName", value = "目录名")
    private String cateName;

    @ApiModelProperty(name="tags", value = "标签数组")
    private List<Tags> tags;

    @ApiModelProperty(name="stateStr", value = "状态字符串")
    private String stateStr;

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String[] getDynamicTags() {
        return dynamicTags;
    }

    public void setDynamicTags(String[] dynamicTags) {
        this.dynamicTags = dynamicTags;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMdContent() {
        return mdContent;
    }

    public void setMdContent(String mdContent) {
        this.mdContent = mdContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }
}
