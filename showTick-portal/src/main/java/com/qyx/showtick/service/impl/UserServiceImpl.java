package com.qyx.showtick.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.bo.CurrentUserDetail;
import com.qyx.showtick.common.entity.User;
import com.qyx.showtick.common.exception.Asserts;
import com.qyx.showtick.common.mapper.UserMapper;
import com.qyx.showtick.mapper.UserDTOMapper;
import com.qyx.showtick.component.JwtUtil;
import com.qyx.showtick.component.SpringUtil;
import com.qyx.showtick.dto.UserDTO;
import com.qyx.showtick.service.UserCacheService;
import com.qyx.showtick.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Yuxin Qin on 7/10/24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    /**
     * user register
     *
     * @param username user name
     * @param password password
     */
    @Override
    public User register(String username, String password) {
        // check if user exists
        User curUser = geUserByUsername(username);
        if(curUser != null){
            Asserts.fail("User already exists");
        }

        // new user, save
        User newUser = new User();
        newUser.setUsername(username);

        String encodePassword = passwordEncoder.encode(password);
        newUser.setPasswordHash(encodePassword);
        userMapper.insert(newUser);
        return newUser;
    }


    @Override
    public String login(String username, String password) {
        String token = null;
        try{
            User user = geUserByUsername(username);
            if(user == null){
                throw new UsernameNotFoundException("username or password incorrect");
            }

            UserDetails userDetails = new CurrentUserDetail(user);
            if(!passwordEncoder.matches(password, userDetails.getPassword())){
                Asserts.fail("password error");
            }

            // todo user enable
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtUtil.generateToken(userDetails.getUsername());
        } catch (AuthenticationException e){
            LOGGER.warn("login error:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public User geUserByUsername(String username) {
        // check redis cache
        User cacheUser = getCacheService().getCurrentUser(username);
        if(cacheUser != null) return cacheUser;

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if(user != null){
            getCacheService().setUser(user); // put in cache
            return user;
        }
        return null;
    }

    /**
     * 获取缓存服务
     */
    @Override
    public UserCacheService getCacheService() {
        return SpringUtil.getBean(UserCacheService.class);
    }

    @Override
    public int updateProfile(String token, UserDTO userDTO) {
        String username = jwtUtil.getUsernameFromToken(token);
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user != null) {
            UserDTOMapper.INSTANCE.updateUserFromDto(userDTO, user);
            getCacheService().setUser(user);
            return userMapper.updateById(user);
        }
        return -1;
    }

}
