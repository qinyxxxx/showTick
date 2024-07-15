package com.qyx.showtick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qyx.showtick.common.mapper")
public class ShowTickPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowTickPortalApplication.class, args);
    }

}
