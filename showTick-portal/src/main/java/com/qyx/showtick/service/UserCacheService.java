package com.qyx.showtick.service;

import com.qyx.showtick.common.entity.User;

/**
 * Created by Yuxin Qin on 7/15/24
 */
public interface UserCacheService {
    User getCurrentUser(String username);

    void setUser(User user);
}
