package com.qyx.showtick.common;

/**
 * Created by Yuxin Qin on 7/14/24
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void testRedis() {
        // 向 Redis 中设置一个键值对
        redisTemplate.opsForValue().set("testKey", "testValue");

        // 从 Redis 中获取设置的值
        String value = redisTemplate.opsForValue().get("testKey");

        if (value!= null && value.equals("testValue")) {
            System.out.println("Redis 配置成功，能够正确存储和获取数据！");
        } else {
            System.out.println("Redis 配置失败，获取数据异常！");
        }
    }
}
