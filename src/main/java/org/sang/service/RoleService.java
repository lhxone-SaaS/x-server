package org.sang.service;

import org.sang.mapper.RoleRouterMapper;
import org.sang.pojo.Role;
import org.sang.mapper.RolesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

@Service
public class RoleService {

    @Resource
    private RolesMapper rolesMapper;

    @Resource
    private RoleRouterMapper roleRouterMapper;

    public List<Role> getRolesByUid(Long uid){

        List<Role> roles = rolesMapper.getRolesByUid(uid);
        // 按ID升序排列
        if(roles.size()>1){
            roles.sort(Comparator.comparing(Role::getId));
        }
        return roles;
    }

    public int addRole(Role role) {
        int count = rolesMapper.addRole(role);
        return count;
    }

    public int updateRole(Role role) {
        int count = rolesMapper.updateRole(role);
        return count;
    }

    public int addRolesForUser(List<Integer> roleIds, Long uid) {
        int count = rolesMapper.setUserRoles(roleIds, uid);
        return count;
    }

    public List<Role> getAllRoles(){
        List<Role> roles = rolesMapper.getAllRoles();
        return roles;
    }

    public List<Role> getUserNotAllocateRoles(Long uid){
        List<Role> notAllocateRoles = rolesMapper.getUserNotAllocateRoles(uid);
        return notAllocateRoles;
    }

    public int delUserRoles(List<Integer> roleIds, Long uid){
        int delCount = rolesMapper.delUserRoles(roleIds, uid);
        return delCount;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delRoleById(Integer roleId){
        rolesMapper.delRoleUserByRoleId(roleId);
        roleRouterMapper.delRoleResourceByRoleId(roleId);
        return rolesMapper.delRoleById(roleId);
    }

}
