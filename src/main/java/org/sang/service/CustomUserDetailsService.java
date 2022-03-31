package org.sang.service;

import org.sang.pojo.CustomUser;
import org.sang.pojo.QueryUserParam;
import org.sang.pojo.RespBean;
import org.sang.pojo.Role;
import org.sang.pojo.dto.UserDTO;
import org.sang.pojo.vo.PageVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface CustomUserDetailsService extends UserDetailsService {

    UserDetails loadUserByPhoneNum(Long phoneNum) throws UsernameNotFoundException;

    int registerUser(UserDTO user);

    int updateUserEmail(String email);

    List<CustomUser> getUserByNickname(String nickname);

    List<Role> getAllRoles();

    int updateUserEnabled(Boolean enabled, Long uid);

    int deleteUserById(Long uid);

    int updateUserRoles(Integer[] rids, Long id);

    CustomUser getUserById(Long id);

    RespBean<PageVo<CustomUser>> queryPageUsersByCondition(PageVo<CustomUser> pageVo, QueryUserParam userParam);


}
