package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.service.RedisService;
import com.qyx.showtick.component.JwtUtil;
import com.qyx.showtick.dto.LoginParam;
import com.qyx.showtick.dto.LoginResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Yuxin Qin on 7/14/24
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisService redisService;

//    @RequestMapping(value = "/login2", method = RequestMethod.POST)
//    public CommonResult login(@RequestBody LoginParam authRequest) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//            String token = jwtUtil.generateToken(authRequest.getUsername());
//            redisTemplate.opsForValue().set(authRequest.getUsername(), token);
//            return CommonResult.success(new LoginResponse(token));
//        } catch (AuthenticationException e) {
//            return CommonResult.failed();//todo
//        }
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());
            redisService.setValue(userDetails.getUsername(), token);
            return token;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
