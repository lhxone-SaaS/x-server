package org.sang.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by sang on 2017/12/17.
 */
@ApiModel(value = "respBean")
public class RespBean<T> implements Serializable {

    @ApiModelProperty(value = "status", name = "状态码", dataType = "int")
    private int status;

    @ApiModelProperty(value = "msg", name = "相应信息", dataType = "String")
    private String msg;

    @ApiModelProperty(value = "data", name = "响应数据", dataType = "Object")
    private T data;

    public RespBean() {
    }

    public RespBean(int status, String msg) {

        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
