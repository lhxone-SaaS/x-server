package org.sang.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * Created by sang on 2017/12/19.
 */
@ApiModel
public class Category {
    @ApiModelProperty(name = "id", value = "id")
    private Long id;

    @ApiModelProperty(name = "cateName", value = "目录名称")
    private String cateName;

    @ApiModelProperty(name="date", value = "日期")
    private Timestamp date;

    public Category() {
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
