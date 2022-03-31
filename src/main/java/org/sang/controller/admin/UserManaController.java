package org.sang.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sang.pojo.RespBean;
import org.sang.pojo.Role;
import org.sang.pojo.CustomUser;
import org.sang.constants.ResponseStateConstant;
import org.sang.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sang on 2017/12/24.
 */
@RestController
@RequestMapping("/admin")
@Api(value = "userManaController", tags = "用户管理相关接口")
public class UserManaController {
    @Resource
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiOperation(value = "getUserByNickname", notes = "查询符合昵称条件的用户集合",
            consumes="application/json", produces = "application/json", response = List.class)
    public List<CustomUser> getUserByNickname(String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @ApiOperation(value = "getUserById", notes = "根据用户Id查询用户信息",
            consumes="application/json", produces = "application/json", response = CustomUser.class)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public CustomUser getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ApiOperation(value = "getAllRole", notes = "获取全部角色", consumes="application/json",
            produces = "application/json", response = List.class)
    public List<Role> getAllRole() {
        return userService.getAllRoles();
    }

    @RequestMapping(value = "/user/enabled", method = RequestMethod.PUT)
    @ApiOperation(value = "updateUserEnabled", notes = "更新用户启用状态",
            consumes="application/json", produces = "application/json", response = RespBean.class)
    public RespBean updateUserEnabled(@RequestParam("enabled") Boolean enabled, @RequestParam("uid") Long uid) {
        if (userService.updateUserEnabled(enabled, uid) == 1) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "更新成功!");
        } else {
            return new RespBean(ResponseStateConstant.SERVER_ERROR, "更新失败!");
        }
    }

    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "deleteUserById", notes = "根据用户ID删除用户", consumes="application/json",
            produces = "application/json", response = RespBean.class)
    public RespBean deleteUserById(@PathVariable Long uid) {
        if (userService.deleteUserById(uid) == 1) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "删除成功!");
        } else {
            return new RespBean(ResponseStateConstant.SERVER_ERROR, "删除失败!");
        }
    }

    @RequestMapping(value = "/user/role", method = RequestMethod.PUT)
    @ApiOperation(value = "updateUserRoles", notes = "更新用户下的角色", consumes="application/json",
            produces = "application/json", response = RespBean.class)
    public RespBean updateUserRoles(Integer[] rids, Long id) {
        if (userService.updateUserRoles(rids, id) == rids.length) {
            return new RespBean(ResponseStateConstant.SERVER_SUCCESS, "更新成功!");
        } else {
            return new RespBean(ResponseStateConstant.SERVER_ERROR, "更新失败!");
        }
    }
}
