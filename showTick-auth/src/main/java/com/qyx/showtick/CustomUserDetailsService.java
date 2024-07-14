package com.qyx.showtick;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Yuxin Qin on 7/14/24
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 实现你自己的用户查找逻辑，例如从数据库查找用户
        // 这里是假设你有一个User类实现了UserDetails接口

        // 示例代码：
        // User user = userRepository.findByUsername(username);
        // if (user == null) {
        //     throw new UsernameNotFoundException("User not found with username: " + username);
        // }
        // return user;

        // 假设用户存在，返回一个用户对象
        // 这里为了演示，直接返回一个没有任何属性的简单用户
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("{noop}password") // 使用{noop}表示不对密码进行加密
                .authorities("USER")
                .build();
    }
}