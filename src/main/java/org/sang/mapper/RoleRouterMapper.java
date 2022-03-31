package org.sang.mapper;

import org.sang.pojo.dto.RouterResourceDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRouterMapper {

    void addRoleRouter(RouterResourceDTO routerResourceDTO);

    List<RouterResourceDTO> queryRoutersByRoleId(Integer roleId);

    List<Integer> queryRouteResourceIdsByRoleId(Integer roleId);

    List<Integer> queryAllRouterResourceIds();

    Integer delRoleResourceByRoleId(Integer roleId);

    Integer addRouteIdsForRole(List<Integer> roleIds, Integer roleId);

}
