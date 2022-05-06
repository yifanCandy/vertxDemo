package com.tgl.access;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * main
 *
 * @author tgl
 * @since 2021-08-13
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.tgl.utils,com.tgl"})
public class AccessServicesMain {
    public static void main(String[] args) {
        SpringApplication.run(AccessServicesMain.class, args);
    }
}
