package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */
@Repository
public interface RolesMapper {

    int addRole(Role role);

    int updateRole(Role role);

    int setUserRoles(@Param("roleIds") List<Integer> roleIds, @Param("uid") Long uid);

    List<Role> getRolesByUid(Long uid);

    List<Role> getAllRoles();

    int deleteUserRolesByUid(Long id);

    List<Role> getUserNotAllocateRoles(Long uid);

    int delUserRoles(@Param("roleIds") List<Integer> roleIds, @Param("uid") Long uid);

    int delRoleById(Integer roleId);

    int delRoleUserByRoleId(Integer roleId);

}
