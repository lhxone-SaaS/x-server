package org.sang.controller;

import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.sang.constants.ResponseStateConstant;
import org.sang.pojo.QueryUserParam;
import org.sang.pojo.RespBean;
import org.sang.pojo.CustomUser;
import org.sang.pojo.vo.PageVo;
import org.sang.service.SmsService;
import org.sang.service.UserService;
import org.sang.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sang on 2017/12/24.
 */
@RestController
@Api(value = "userController", tags = "用户相关接口")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private SmsService smsService;

    @RequestMapping(value = "/currentUserName", method = RequestMethod.GET)
    @ApiOperation(value = "currentUserName", notes = "获取当前用户名称", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean<String> currentUserName() {
        RespBean<String> respBean = new RespBean<>(200, "ok");
        respBean.setData(Util.getCurrentUser().getNickname());
        return respBean;
    }

    @RequestMapping(value = "/currentUserId", method = RequestMethod.GET)
    @ApiOperation(value = "currentUserId", notes = "获取当前用户ID", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean<Long> currentUserId() {
        RespBean<Long> respBean = new RespBean<>(200, "ok");
        respBean.setData(Util.getCurrentUser().getId());
        return respBean;
    }

    @RequestMapping(value = "/currentUserEmail", method = RequestMethod.GET)
    @ApiOperation(value = "currentUserEmail", notes = "获取当前用户邮箱",
            produces = "application/json", consumes = "application/json", response = RespBean.class)
    public RespBean<String> currentUserEmail() {
        RespBean<String> respBean = new RespBean<>(200, "ok");
        respBean.setData(Util.getCurrentUser().getEmail());
        return respBean;
    }

    @RequestMapping(value = "/isAdmin", method = RequestMethod.GET)
    @ApiOperation(value = "isAdmin", notes = "判断当前用户是否管理员", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean<Boolean> isAdmin() {
        List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
        boolean flag = false;
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains("superAdmin")) {
                flag = true;
                break;
            }
        }
        RespBean<Boolean> respBean = new RespBean<>(200, "ok");
        respBean.setData(flag);
        return respBean;
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    @ApiOperation(value = "userInfo", notes = "获取当前用户信息", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean<CustomUser> userInfo(){
        RespBean<CustomUser> respBean = new  RespBean<>(ResponseStateConstant.SERVER_SUCCESS,"ok");
        CustomUser user = Util.getCurrentUser();
        respBean.setData(user);
        return respBean;
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    @ApiOperation(value = "logout", notes = "退出登录", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean<String> logout() {
        logger.info("退出登录");
        Util.clearContext();
        RespBean<String> respBean = new RespBean<>(ResponseStateConstant.SERVER_SUCCESS,
                "退出登录成功");
        respBean.setData("success");
        return respBean;
    }

    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    @ApiOperation(value = "updateUserEmail", notes = "开启用户邮箱", produces = "application/json", consumes = "application/json")
    @ApiImplicitParam(name = "email", value = "邮箱地址", dataType = "java.lang.String", paramType = "query",
            required = true, example = "sang2021@163.com")
    public RespBean updateUserEmail(@RequestParam("email") String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "开启成功!");
        }
        return new RespBean(ResponseStateConstant.SERVER_ERROR, "开启失败!");
    }

    @RequestMapping(value = "/pageQuery/users/{currPage}/{pageSize}",method = RequestMethod.POST)
    @ApiOperation(value = "queryPageUsersByCondition", notes = "分页查询用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name="currPage", value = "当前页", required = true, dataType = "int", example = "1"),
    @ApiImplicitParam(name="pageSize",value = "每页数据条数", required = true, dataType = "int", example = "10"),
    @ApiImplicitParam(name="userParam", value = "用户查询参数", required = true, paramType = "body", dataTypeClass=QueryUserParam.class),})
    public RespBean<PageVo<CustomUser>> queryPageUsersByCondition(@PathVariable("currPage") int currPage, @PathVariable("pageSize") int pageSize,
                                                                  @RequestBody QueryUserParam userParam){
        PageVo<CustomUser> pageVo = new PageVo<>(currPage,pageSize);
        RespBean<PageVo<CustomUser>> respBean = userService.queryPageUsersByCondition(pageVo,userParam);
        return respBean;
    }

    @RequestMapping(value="sendLoginVerifyCode", method = RequestMethod.POST)
    @ApiOperation(value = "sendLoginVerifyCode", notes = "发送登录短信验证码")
    @ApiImplicitParam(name="paramMap", value = "入参Map", required = true, paramType = "body", dataTypeClass=HashMap.class)
    public RespBean<Map<String, Object>> sendLoginVerifyCode(@RequestBody Map<String, String> paramMap) {
        String phoneNumber = paramMap.get("phoneNumber");
        SendSmsResponse response = smsService.sendLoginVeryCodeMessage(phoneNumber);
        SendStatus[] statuses = response.getSendStatusSet();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", statuses[0].getCode());
        resultMap.put("message", statuses[0].getMessage());
        resultMap.put("phoneNumber", statuses[0].getPhoneNumber());
        resultMap.put("fee", statuses[0].getFee());
        RespBean<Map<String, Object>> respBean = new RespBean<>(200, "success");
        respBean.setData(resultMap);
        return respBean;
    }

}
