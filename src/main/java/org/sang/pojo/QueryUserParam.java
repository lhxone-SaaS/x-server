package org.sang.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "queryUserParam")
public class QueryUserParam implements Serializable {

    @ApiModelProperty(name="username", value = "用户名", notes = "用户名", dataType = "string")
    private String username;

    @ApiModelProperty(name="nickname", value = "用户昵称", notes = "用户昵称", dataType = "string")
    private String nickname;

    @ApiModelProperty(name="phoneNum", value = "手机号码", notes = "手机号码", dataType = "Long")
    private Long phoneNum;

    @ApiModelProperty(name="email", value = "邮箱地址", notes = "邮箱地址", dataType = "string")
    private String email;

    @ApiModelProperty(name="regStartTime", value = "开始注册时间", notes = "开始注册时间", dataType = "datetime")
    private String regStartTime;

    @ApiModelProperty(name="regEndTime", value = "结束注册时间", notes = "结束注册时间", dataType = "datetime")
    private String regEndTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegStartTime() {
        return regStartTime;
    }

    public void setRegStartTime(String regStartTime) {
        this.regStartTime = regStartTime;
    }

    public String getRegEndTime() {
        return regEndTime;
    }

    public void setRegEndTime(String regEndTime) {
        this.regEndTime = regEndTime;
    }
}
