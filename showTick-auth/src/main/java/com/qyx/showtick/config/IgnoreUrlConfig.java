package com.qyx.showtick.config;

/**
 * Created by Yuxin Qin on 7/16/24
 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlConfig {
    private List<String> urls = new ArrayList<>();
}