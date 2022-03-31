package org.sang.utils;

import org.sang.pojo.CustomUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by sang on 2017/12/20.
 */
public class Util {
    public static CustomUser getCurrentUser() {
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public static void clearContext(){
        SecurityContextHolder.clearContext();
    }
}
