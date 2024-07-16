package com.qyx.showtick.common.service;


/**
 * Created by Yuxin Qin on 7/14/24
 */

public interface RedisService {
    void setValue(String key, Object value);
    void setValue(String key, Object value, Long time);

    Object getValue(String key);

    Boolean deleteValue(String key);
}
