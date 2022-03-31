package org.sang.service;

import com.alibaba.fastjson.JSON;
import org.sang.mapper.RolesMapper;
import org.sang.mapper.UserMapper;
import org.sang.pojo.QueryUserParam;
import org.sang.pojo.RespBean;
import org.sang.pojo.Role;
import org.sang.pojo.CustomUser;
import org.sang.pojo.dto.UserDTO;
import org.sang.pojo.vo.PageVo;
import org.sang.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by sang on 2017/12/17.
 */
@Service
@Transactional
public class UserService implements CustomUserDetailsService {
    @Resource
    UserMapper userMapper;
    @Resource
    RolesMapper rolesMapper;
    @Resource
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 根据username查询用户详细信息
     * @param username
     * @return customUser
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户登录认证, username={}", username);
        UserDTO userDTO = userMapper.loadUserByUsername(username);
        if (userDTO == null) {
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            throw  new UsernameNotFoundException("Username" + username + "is invalid!");
        }
        CustomUser customUser = convertUserDTO2CustomUser(userDTO);
        return customUser;
    }

    /**
     * UserDTO转CustomUser对象
     * @param userDTO
     * @return user
     */
    private CustomUser convertUserDTO2CustomUser(UserDTO userDTO) {
        //查询用户的角色信息，并返回存入user中
        List<Role> roles = rolesMapper.getRolesByUid(userDTO.getId());
        // 权限大的角色排在前面
        roles.sort(Comparator.comparing(Role::getId));
        CustomUser user = new CustomUser(userDTO.getUsername(), userDTO.getPassword(),
                userDTO.getEnabled()==1, true, true,
                true, new ArrayList<>());
        user.setId(userDTO.getId());
        user.setNickname(userDTO.getNickname());
        user.setPhoneNum(userDTO.getPhoneNum());
        user.setEmail(userDTO.getEmail());
        user.setUserface(userDTO.getUserface());
        user.setRegTime(userDTO.getRegTime());
        user.setUpdateTime(userDTO.getUpdateTime());
        user.setRoles(roles);
        user.setCurrentRole(roles.get(0));
        return user;
    }

    /**
     * 根据用户手机号查询用户详细信息
     * @param phoneNum 手机号
     * @return customUser
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByPhoneNum(Long phoneNum) throws UsernameNotFoundException {
        logger.info("用户登录认证, phoneNum={}", phoneNum);
        UserDTO userDTO = userMapper.loadUserByPhoneNum(phoneNum);
        if (userDTO == null) {
            // 抛UsernameNotFoundException异常
            throw  new UsernameNotFoundException("user " + phoneNum + " not exist!");
        }
        CustomUser customUser = convertUserDTO2CustomUser(userDTO);
        return customUser;
    }

    /**
     * @param user
     * @return 0表示成功
     * 1表示用户名重复
     * 2表示失败
     * 3 注册失败
     */
    @Override
    public int registerUser(UserDTO user) {
        UserDTO userDTO  = userMapper.loadUserByUsername(user.getUsername());
        if (userDTO != null) {
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);//用户可用
        int result = userMapper.registerUser(user);
        //配置用户的角色，默认都是普通用户
        List<Integer> roleIds = Arrays.asList(2);
        int i = rolesMapper.setUserRoles(roleIds, user.getId());
        boolean b = i == roleIds.size() && result == 1;
        if (b) {
            // 注册成功
            return 0;
        } else {
            // 注册失败
            return 2;
        }
    }

    @Override
    public int updateUserEmail(String email) {
        return userMapper.updateUserEmail(email, Util.getCurrentUser().getId());
    }

    @Override
    public List<CustomUser> getUserByNickname(String nickname) {
        List<CustomUser> list = userMapper.getUserByNickname(nickname);
        return list;
    }

    @Override
    public List<Role> getAllRoles() {
        return rolesMapper.getAllRoles();
    }

    @Override
    public int updateUserEnabled(Boolean enabled, Long uid) {
        return userMapper.updateUserEnabled(enabled, uid);
    }

    @Override
    public int deleteUserById(Long uid) {
        return userMapper.deleteUserById(uid);
    }

    @Override
    public int updateUserRoles(Integer[] rids, Long id) {
        int i = rolesMapper.deleteUserRolesByUid(id);
        List<Integer> roleIds = Arrays.asList(rids);
        return rolesMapper.setUserRoles(roleIds, id);
    }

    @Override
    public CustomUser getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public RespBean<PageVo<CustomUser>> queryPageUsersByCondition(PageVo<CustomUser> pageVo, QueryUserParam userParam){
        logger.info("currPage={},pageSize={},queryUserParam={}",
                pageVo.getCurrPage(),pageVo.getPageSize(), JSON.toJSON(userParam));
        int totalRows = userMapper.queryUserCountsByCondition(userParam);
        pageVo.setTotalRows(totalRows);
        if(pageVo.getEndIndex()>totalRows){
            pageVo.setEndIndex(totalRows);
        }
        pageVo.setTotalPage((totalRows/pageVo.getPageSize())+1);
        List<CustomUser> pageUsers = userMapper.queryPageUsersByCondition(pageVo.getStartIndex(),pageVo.getEndIndex(),userParam);
        pageVo.setResult(pageUsers);
        RespBean<PageVo<CustomUser>> respBean = new RespBean<>(200,"success");
        respBean.setData(pageVo);
        return respBean;
    }
}
