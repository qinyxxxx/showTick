package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.User;
import com.qyx.showtick.dto.UserDTO;
import com.qyx.showtick.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuxin Qin on 7/10/24
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UserService userService;

//    @Operation(summary = "会员注册")
    @RequestMapping(value = "/sso/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestParam String username,
                                 @RequestParam String password) {
        userService.register(username, password);
        return CommonResult.success(null,"Register success");
    }


    @RequestMapping(value = "/sso/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestParam String username,
                              @RequestParam String password) {
        String token = userService.login(username, password);
        if(token == null){
            return CommonResult.unauthorized("Username or password incorrect");
        }

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    // get current user info
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getProfile(Principal principal) {
        if(principal==null){
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        User user = userService.geUserByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("gender", user.getGender());
        data.put("email", user.getEmail());
        data.put("mobile", user.getMobile());
        data.put("address", user.getAddress());
        return CommonResult.success(data);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult updateProfile(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        int count = userService.updateProfile(token, userDTO);
        if(count > 0){
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}
