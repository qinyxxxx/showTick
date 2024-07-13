package com.qyx.showtickcommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtickcommon.entity.User;
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
    void register(String username, String password);

    User login(String username, String password);

    public User getByUsername(String username);
}
