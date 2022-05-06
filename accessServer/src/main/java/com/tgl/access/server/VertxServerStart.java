package com.tgl.access.server;

import com.tgl.access.configuration.ServerConfiguration;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * main
 *
 * @author tgl
 * @since 2021/8/13
 */
@Component
public class VertxServerStart implements CommandLineRunner {
    @Autowired
    ServerConfiguration serverConf;

    @Override
    public void run(String... args) throws Exception {
        Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setInstances(serverConf.getEventLoopPoolSize());
        deploymentOptions.setWorkerPoolName("woker-pool");
        deploymentOptions.setWorkerPoolSize(serverConf.getWorkerPoolSize());
        deploymentOptions.setMaxWorkerExecuteTime(serverConf.getMaxWorkerExecuteTime());
        vertx.deployVerticle(VertxHttpServer.class.getName(), deploymentOptions);
    }
}
