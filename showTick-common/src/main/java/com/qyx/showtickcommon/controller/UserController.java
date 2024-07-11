package com.qyx.showtickcommon.controller;

import com.qyx.showtickcommon.api.CommonResult;
import com.qyx.showtickcommon.mapper.UserMapper;
import com.qyx.showtickcommon.service.UserService;
import com.qyx.showtickcommon.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/10/24
 */
@RestController
@RequestMapping("/api/users")
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
        User user = userMapper.selectById(1);
        return user;
    }

//    @Operation(summary = "会员注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String mobile) {
        userService.register(username, password, mobile);
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
