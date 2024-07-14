package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.component.JwtUtil;
import com.qyx.showtick.dto.LoginParam;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public CommonResult login(@RequestBody LoginParam authRequest) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//            String token = jwtUtil.generateToken(authRequest.getUsername());
//            redisTemplate.opsForValue().set(authRequest.getUsername(), token);
//            return CommonResult.success(new AuthResponse(token));
//        } catch (AuthenticationException e) {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//todo
//        }
//    }

}
