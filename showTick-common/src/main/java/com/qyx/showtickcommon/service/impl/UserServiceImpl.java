package com.qyx.showtickcommon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtickcommon.exception.Asserts;
import com.qyx.showtickcommon.service.UserService;
import com.qyx.showtickcommon.entity.User;
import com.qyx.showtickcommon.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Created by Yuxin Qin on 7/10/24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * user register
     *
     * @param username user name
     * @param password password
     */
    @Override
    public void register(String username, String password) {
        // check if user exists
        List<User> existUsers = userMapper.selectList(new QueryWrapper<User>().eq("username", username));
        if(!existUsers.isEmpty()){
            Asserts.fail("User already exists");
        }
        // new user, save
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(password);
        userMapper.insert(newUser);
    }


    @Override
    public User login(String username, String password) {
        String token = null;
        // todo 加密，token验证
//        User user = getByUsername(username);
        User user = userMapper.selectOne(
                new QueryWrapper<User>()
                        .eq("username", username)
                        .eq("password_hash", password));
        return user; // todo return token
    }

    @Override
    public User getByUsername(String username) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        return user;
    }
}
