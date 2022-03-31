package org.sang.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sang.pojo.RespBean;
import org.sang.pojo.vo.RouterResourceVo;
import org.sang.constants.ResponseStateConstant;
import org.sang.service.RoleRouterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routerResource")
@Api(value = "routerResourceController", tags = "路由资源相关接口", produces = "application/json", consumes = "application/json")
public class RouterResourceController {

    private static final Logger logger = LoggerFactory.getLogger(RouterResourceController.class);

    @Autowired
    private RoleRouterService roleRouterService;

    /**
     * 获取当前角色的路由资源列表
     * @param roleId
     * @return
     */
    @GetMapping("/currentRoleResources")
    @ApiOperation(value = "queryCurrentRoleRouters", notes = "获取当前角色路由列表", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean queryCurrentRoleRouters(@RequestParam("roleId") Integer roleId){
        logger.info("roleId={}",roleId);
        if(roleId==null){
            throw new IllegalArgumentException("roleId cannot be null");
        }
        RespBean respBean = new RespBean(ResponseStateConstant.SERVER_SUCCESS,"查询成功");
        List<RouterResourceVo> routerResourceVos = roleRouterService.queryRoleRoutersByRoleId(roleId);
        respBean.setData(routerResourceVos);
        return respBean;
    }

    @GetMapping("/currentRoleResourceIds")
    @ApiOperation(value = "getResourceIdsByRoleId", notes = "根据角色ID获取角色下的路由列表", produces = "application/json",
            consumes = "application/json", response = RespBean.class)
    public RespBean<List<String>> getResourceIdsByRoleId(@RequestParam("roleId") Integer roleId){
        logger.info("roleId={}",roleId);
        List<String> data = roleRouterService.queryCurrentRoleResourceIds(roleId);
        RespBean<List<String>> respBean = new RespBean(ResponseStateConstant.SERVER_SUCCESS,"查询成功");
        respBean.setData(data);
        return respBean;
    }

    @GetMapping("/allRouterIds")
    @ApiOperation(value = "getAllRouterIds", notes = "获取全部路由ID", response = RespBean.class)
    public RespBean<List<Integer>> getAllRouterIds(){
        List<Integer> routerIds = roleRouterService.queryAllRouterIds();
        RespBean<List<Integer>> respBean = new RespBean<>(200,"success");
        respBean.setData(routerIds);
        return respBean;
    }

    @PostMapping("/addRouteIds")
    @ApiOperation(value = "addRouteIdsForRole", notes = "给角色添加路由资源", response = RespBean.class)
    public RespBean<Integer> addRouteIdsForRole(@RequestBody List<Integer> routeIds, @RequestParam("roleId") Integer roleId){
        logger.info("http request addRouteIds start");
        logger.info("roleId={}",roleId);
        RespBean<Integer> respBean = new RespBean<>(200, "success");
        Integer count = roleRouterService.addRouteIdsForRole(routeIds, roleId);
        respBean.setData(count);
        return respBean;
    }

}
