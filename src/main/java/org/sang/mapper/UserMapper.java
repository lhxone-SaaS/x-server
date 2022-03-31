package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.pojo.QueryUserParam;
import org.sang.pojo.CustomUser;
import org.sang.pojo.dto.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */
@Repository
public interface UserMapper {

    UserDTO loadUserByUsername(@Param("username") String username);

    UserDTO loadUserByPhoneNum(@Param("phoneNum") Long phoneNum);

    int registerUser(UserDTO user);

    int updateUserEmail(@Param("email") String email, @Param("id") Long id);

    List<CustomUser> getUserByNickname(@Param("nickname") String nickname);

    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

    int deleteUserById(Long uid);

    CustomUser getUserById(@Param("id") Long id);

    int queryUserCountsByCondition(@Param("userParam") QueryUserParam userParam);

    List<CustomUser> queryPageUsersByCondition(@Param("startIndex") int startIndex, @Param("endIndex") int endIndex,
                                               @Param("userParam") QueryUserParam userParam);
}
