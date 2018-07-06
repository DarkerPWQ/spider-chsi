package com.pwq.chsi.center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by johnny on 2017/6/9.
 */
@Configuration
public class ProcessorMapConfig {
    @Bean
    public Map<String, String> processorMap() {
        Map<String, String> map = new HashMap<>();
        map.put("gaokao", "gaokaoProcessor");

        return map;
    }
}
