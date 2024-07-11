package com.qyx.showtickcommon.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Yuxin Qin
 * @version v1.0
 * @date 7/9/24
 */

@Configuration
//@EnableTransactionManagement
@MapperScan("com.qyx.showtickcommon.mapper")
public class MyBatisPlusConfig {
}
