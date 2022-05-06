package com.tgl.access.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * main
 *
 * @author tgl
 * @since 2021/8/13
 */
@Configuration
@PropertySource(value = "classpath:server.properties", encoding = "UTF8")
@Getter
public class ServerConfiguration {
    @Value("${vertx.server.options.eventLoopPoolSize}")
    private int eventLoopPoolSize;

    @Value("${vertx.server.options.workerPoolSize}")
    private int workerPoolSize;

    @Value("${vertx.server.hosts}")
    private String hosts;

    @Value("${vertx.server.port}")
    private int port;

    @Value("${vertx.server.idleTimeout}")
    private int idleTimeout;

    @Value("${vertx.server.maxWorkerExecuteTime}")
    private int maxWorkerExecuteTime;

    @Value("${vertx.client.connectTimeout}")
    private int connectTimeout;
}
