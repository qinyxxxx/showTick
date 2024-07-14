package com.qyx.showtick.common.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.mapper.UserMapper;
import com.qyx.showtick.common.service.UserService;
import com.qyx.showtick.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/10/24
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

//    @Operation(summary = "获取指定订单设置")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(@PathVariable Long id) {
//        User user = userService.getById(id);
        User user = userMapper.selectById(id);
        return user;
    }

//    @Operation(summary = "会员注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestParam String username,
                                 @RequestParam String password) {
        userService.register(username, password);
        return CommonResult.success(null,"Register success");
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password) {
        User user = userService.login(username, password);
        // todo token login
        if (user == null) {
            return CommonResult.validateFailed("username or password incorrect");
        }
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("token", token);
//        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(user, "login success");
    }


}
