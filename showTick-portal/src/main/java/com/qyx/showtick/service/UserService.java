package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.User;
import com.qyx.showtick.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * User management service
 * Created by Yuxin Qin on 7/10/24
 */
public interface UserService extends IService<User> {
    /**
     * user register
     */
    @Transactional
    User register(String username, String password);

    String login(String username, String password);

    User geUserByUsername(String username);

    UserCacheService getCacheService();

    @Transactional
    int updateProfile(String token, UserDTO userDTO);

}
