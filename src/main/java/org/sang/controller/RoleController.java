package org.sang.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.sang.pojo.RespBean;
import org.sang.pojo.Role;
import org.sang.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/role")
@Api(value="roleController", tags = "角色相关API")
public class RoleController {

    @Autowired
    private RoleService roleService;

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @PostMapping(path = "/addRole")
    @ApiOperation(value = "addRole", notes = "添加角色", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    @ApiImplicitParam(name="role", value = "角色对象", dataTypeClass = Role.class, paramType="body", required = true)
    public RespBean<Integer> addRole(@RequestBody Role role) {
        logger.info("roleCode={},roleName={}",role.getRoleCode(),role.getRoleName());
        int addCount = roleService.addRole(role);
        RespBean<Integer> respBean = new RespBean<>(200, "success");
        respBean.setData(addCount);
        return respBean;
    }

    @PostMapping(path = "/updateRole")
    @ApiOperation(value = "updateRole", notes = "修改角色", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    @ApiImplicitParam(name="role", value = "角色对象", dataTypeClass = Role.class, paramType="body", required = true)
    public RespBean<Integer> updateRole(@RequestBody Role role) {
        logger.info("roleCode={},roleName={}",role.getRoleCode(),role.getRoleName());
        int updateCount = roleService.updateRole(role);
        RespBean<Integer> respBean = new RespBean<>(200, "success");
        respBean.setData(updateCount);
        return respBean;
    }

    @DeleteMapping("/delRole/{roleId}")
    @ApiOperation(value = "delRoleById", notes = "删除角色", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    @ApiImplicitParam(name="roleId", value = "角色ID", required = true, paramType = "path", dataType = "java.lang.Integer")
    public RespBean<Integer> delRoleById(@PathVariable Integer roleId){
        logger.info("roleId={}", roleId);
        RespBean<Integer> respBean = new RespBean<>(200,"success");
        Integer count = roleService.delRoleById(roleId);
        respBean.setData(count);
        return respBean;
    }

    @PostMapping(path = "/addRolesForUser")
    @ApiOperation(value = "addRolesForUser", notes = "为用户添加角色", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    @ApiImplicitParams({@ApiImplicitParam(name="roleIds",value = "角色ID集合", dataType = "java.util.List", paramType = "body", required = true),
    @ApiImplicitParam(name="uid",value = "用户ID", dataType = "java.lang.Long", paramType = "query", required = true)})
    public RespBean addRolesForUser(@RequestBody List<Integer> roleIds, @RequestParam("uid") Long uid) {
        logger.info("uid={}",uid);
        int addCount = roleService.addRolesForUser(roleIds, uid);
        RespBean<Integer> respBean = new RespBean<>(200, "success");
        respBean.setData(addCount);
        return respBean;
    }

    @GetMapping("/getUserRoles")
    @ApiOperation(value="getUserRoles", notes = "根据用户ID获取用户角色列表",
            produces = "application/json", consumes = "application/json", response = RespBean.class)
    @ApiImplicitParam(name="uid", value = "用户ID", dataType = "java.lang.Long",
            paramType = "query", required = true, example = "2")
    public RespBean<List<Role>> getUserRoles(@RequestParam("uid") Long uid) {
        logger.info("uid={}",uid);
        List<Role> roles = roleService.getRolesByUid(uid);
        RespBean<List<Role>> respBean = new RespBean<>(200, "success");
        respBean.setData(roles);
        return respBean;
    }

    @GetMapping("/allRoles")
    @ApiOperation(value = "getAllRoles", notes = "获取所有角色列表", produces = "application/json", consumes = "application/json", response = RespBean.class)
    public RespBean<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        RespBean<List<Role>> respBean = new RespBean<>(200, "success");
        respBean.setData(roles);
        return respBean;
    }

    @GetMapping("/userNotAllocateRoles")
    @ApiOperation(value = "getUserNotAllocateRoles", notes = "获取用户未授权角色列表",
            produces = "application/json", consumes = "application/json", response = RespBean.class)
    @ApiImplicitParam(name="uid", value = "用户ID", dataType = "java.lang.Long",
            paramType = "query", required = true, example = "2")
    public RespBean<List<Role>> getUserNotAllocateRoles(@RequestParam("uid") Long uid){
        logger.info("uid={}",uid);
        List<Role> roles = roleService.getUserNotAllocateRoles(uid);
        RespBean<List<Role>> respBean = new RespBean<>(200, "success");
        respBean.setData(roles);
        return respBean;
    }

    @DeleteMapping("/userRoles")
    @ApiOperation(value = "delUserRoles", notes = "删除用户角色", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    @ApiImplicitParams({@ApiImplicitParam(name="roleIds", value = "角色ID列表", dataType = "java.util.List",
            paramType = "body", required = true, example = "[1,2,3]"),
    @ApiImplicitParam(name="uid", value = "用户ID", dataType = "java.lang.Long", required = true, example = "2")})
    public RespBean<Integer> delUserRoles(@RequestBody List<Integer> roleIds, @RequestParam("uid") Long uid){
        logger.info("uid={}",uid);
        int delCount = roleService.delUserRoles(roleIds, uid);
        RespBean<Integer> respBean = new RespBean<>(200, "success");
        respBean.setData(delCount);
        return respBean;
    }
}
