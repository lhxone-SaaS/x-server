package org.sang.service;

import org.sang.pojo.dto.RouterResourceDTO;
import org.sang.pojo.vo.RouterResourceVo;
import org.sang.mapper.RoleRouterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleRouterService {

    @Autowired
    private RoleRouterMapper roleRouterMapper;

    public List<RouterResourceVo> queryRoleRoutersByRoleId(Integer roleId){
        List<RouterResourceVo> routerResourceVos = new ArrayList<>();
        List<RouterResourceDTO> routerResourceDTOS = roleRouterMapper.queryRoutersByRoleId(roleId);
        if(routerResourceDTOS.size()==0){
            return routerResourceVos;
        }
        Integer maxLevel = routerResourceDTOS.get(routerResourceDTOS.size()-1).getLevel();
        // 将不同层级的路由数组对象放到一个map里面
        Map<String,List<RouterResourceDTO>> levelRoutersMap = new HashMap<>();
        for(int i=1;i<maxLevel+1;i++){
            levelRoutersMap.put("level"+i,new ArrayList<>());
        }
        for(RouterResourceDTO routerResourceDTO : routerResourceDTOS){
            int levelNum = routerResourceDTO.getLevel();
            levelRoutersMap.get("level"+levelNum).add(routerResourceDTO);
        }
        // 在每一层的下一层节点列表中找节点的子节点，避免了递归
        for(int level=1;level<maxLevel;level++){
             List<RouterResourceDTO> levelRouters = levelRoutersMap.get("level"+level);
             List<RouterResourceDTO> levelChildrenRouters = levelRoutersMap.get("level"+(level+1));
             if(level==1){
                 for(RouterResourceDTO routerResourceDTO : levelRouters){
                     RouterResourceVo routerResourceVo = buildRoleRouteVoByRoleRouter(routerResourceDTO);
                     for(RouterResourceDTO childRouter: levelChildrenRouters){
                         if(childRouter.getPid()== routerResourceDTO.getId()){
                             RouterResourceVo childRouterVo = buildRoleRouteVoByRoleRouter(childRouter);
                             routerResourceVo.getChildren().add(childRouterVo);
                         }
                     }
                     routerResourceVos.add(routerResourceVo);
                 }
             }else{
                 for(RouterResourceDTO routerResourceDTO : levelRouters){
                     RouterResourceVo routerResourceVo = buildRoleRouteVoByRoleRouter(routerResourceDTO);
                     for(RouterResourceDTO childRouter: levelChildrenRouters){
                         if(childRouter.getPid()== routerResourceDTO.getId()){
                             RouterResourceVo childRouterVo = buildRoleRouteVoByRoleRouter(childRouter);
                             routerResourceVo.getChildren().add(childRouterVo);
                         }
                     }
                 }
             }
        }
        sortResourceVo(routerResourceVos);
        return routerResourceVos;
    }

    private void sortResourceVo(List<RouterResourceVo> routerResourceVos){
        if(routerResourceVos.size()>1){
            routerResourceVos.sort(Comparator.comparing(RouterResourceVo::getId));
        }
        for(RouterResourceVo resourceVo: routerResourceVos){
            if(resourceVo.getChildren().size()>1){
                sortResourceVo(resourceVo.getChildren());
            }
        }
    }

    private RouterResourceVo buildRoleRouteVoByRoleRouter(RouterResourceDTO routerResourceDTO){
        RouterResourceVo routerResourceVo = new RouterResourceVo();
        routerResourceVo.setId(routerResourceDTO.getId());
        routerResourceVo.setName(routerResourceDTO.getName());
        routerResourceVo.setTitle(routerResourceDTO.getTitle());
        routerResourceVo.setIcon(routerResourceDTO.getIcon());
        routerResourceVo.setHidden(routerResourceDTO.getHidden()==1);
        routerResourceVo.setPath(routerResourceDTO.getPath());
        routerResourceVo.setRedirect(routerResourceDTO.getRedirect());
        routerResourceVo.setComponentUrl(routerResourceDTO.getComponentUrl());
        routerResourceVo.setLevel(routerResourceDTO.getLevel());
        routerResourceVo.setKeepAlive(routerResourceDTO.getKeepAlive()==1);
        return routerResourceVo;
    }


    public List<String> queryCurrentRoleResourceIds(Integer roleId){
        List<Integer> resourceIds = roleRouterMapper.queryRouteResourceIdsByRoleId(roleId);
        List<String> resultList = new ArrayList<>();
        for(Integer resourceId: resourceIds){
            resultList.add(String.valueOf(resourceId));
        }
        return resultList;
    }

    public List<Integer> queryAllRouterIds(){
        List<Integer> resourceIds = roleRouterMapper.queryAllRouterResourceIds();
        return resourceIds;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer addRouteIdsForRole(List<Integer> routeIds, Integer roleId){
        // 删除原来的角色-路由资源关系
        roleRouterMapper.delRoleResourceByRoleId(roleId);
        Integer count =  roleRouterMapper.addRouteIdsForRole(routeIds, roleId);
        return count;
    }

}
