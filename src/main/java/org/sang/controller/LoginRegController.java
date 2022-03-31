package org.sang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sang.pojo.RespBean;
import org.sang.constants.ResponseStateConstant;
import org.sang.pojo.dto.UserDTO;
import org.sang.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by sang on 2017/12/17.
 */
@RestController
@Api(value = "loginRegController", tags = "登录相关接口")
public class LoginRegController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/login_error", method = RequestMethod.GET)
    @ApiOperation(value = "loginError", notes = "登录失败", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean loginError() {
        return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "登录失败!");
    }

    @RequestMapping(value = "/login_success", method = RequestMethod.GET)
    @ApiOperation(value = "loginSuccess", notes = "登录成功", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean loginSuccess() {
        return new RespBean(ResponseStateConstant.SERVER_ERROR, "登录成功!");
    }

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     * <p>
     * 如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面
     *
     * @return
     */
    @RequestMapping(value = "/login_page", method = RequestMethod.GET)
    @ApiOperation(value = "loginPage", notes = "尚未登录跳转", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean loginPage() {
        return new RespBean(ResponseStateConstant.UN_AUTHORIZED, "尚未登录，请登录!");
    }

    @PostMapping("/user/reg")
    @ApiOperation(value = "reg", notes = "用户注册", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean reg(@RequestBody UserDTO user) {
        int result = userService.registerUser(user);
        if (result == 0) {
            //成功
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "注册成功!");
        } else if (result == 1) {
            return new RespBean(ResponseStateConstant.DUPLICATE_ERROR, "用户名重复，注册失败!");
        } else {
            //失败
            return new RespBean(ResponseStateConstant.SERVER_ERROR, "注册失败!");
        }
    }

}
