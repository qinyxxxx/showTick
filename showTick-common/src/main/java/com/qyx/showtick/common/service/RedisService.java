package com.qyx.showtick.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Yuxin Qin on 7/14/24
 */

public interface RedisService {
    void setValue(String key, Object value);
    void setValue(String key, Object value, Long time);


    Object getValue(String key);

    void deleteValue(String key);
}
