package com.qyx.showtick.common;

import com.qyx.showtick.common.entity.User;
import com.qyx.showtick.common.mapper.UserMapper;
import com.qyx.showtick.common.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EventTickCommonApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Test
    public void select() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void redisTest1(){
        redisService.setValue("123", "qyx");
        System.out.println(redisService.getValue("123"));
    }

}
