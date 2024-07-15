package com.qyx.showtick.service.impl;

import com.qyx.showtick.common.entity.User;
import com.qyx.showtick.common.service.RedisService;
import com.qyx.showtick.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Yuxin Qin on 7/15/24
 */
@Service
public class UserCacheServiceImpl implements UserCacheService {

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.user}")
    private String REDIS_KEY_USER;

    @Autowired
    private RedisService redisService;

    @Override
    public User getCurrentUser(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + username;
        return (User) redisService.getValue(key);
    }

    @Override
    public void setUser(User user) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_USER + ":" + user.getUsername();
        redisService.setValue(key, user, REDIS_EXPIRE);
    }
}
