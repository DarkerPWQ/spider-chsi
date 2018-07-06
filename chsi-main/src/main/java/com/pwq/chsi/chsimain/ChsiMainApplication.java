package com.pwq.chsi.chsimain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties()
@ComponentScan(basePackages = "com.pwq.chsi")
@PropertySource(value = {"file:/data/pwq/config/spider-chsi.properties"})
public class ChsiMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChsiMainApplication.class, args);
	}
}
