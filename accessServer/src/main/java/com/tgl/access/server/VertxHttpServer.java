package com.tgl.access.server;

import com.tgl.access.configuration.ServerConfiguration;
import com.tgl.utils.SpringUtils;
import com.tgl.verticle.BaseVerticle;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;


/**
 * VertxHttpServer
 *
 * @author tgl
 * @since 2021/8/13
 */
public class VertxHttpServer extends BaseVerticle {
    private static Logger log = LogManager.getLogger(VertxHttpServer.class.getName());

    private ServerConfiguration severConf = SpringUtils.getBean(ServerConfiguration.class);

    @Override
    public void serverManage(Router router) {
        if (router == null) {
            log.error("vertx server router is null");
            return;
        }

        registerGetReq(router);

        registerPostReq(router);

        processInvalidPath(router);

        HttpServer server = vertx.createHttpServer(createServerOptions());

        server.connectionHandler(connection -> {
            SocketAddress remote = connection.remoteAddress();
            log.info("receive connection [{}:{}]", remote.host(), remote.port());
            connection.closeHandler(conn -> {
                log.info("connection [{}:{}] closed.", remote.host(), remote.port());
            }).exceptionHandler(ex -> {
                log.error("connection occurred exception:{}", ex.getMessage());
            });
        });

        server.requestHandler(router);

        server.listen(result -> {
            if (result.succeeded()) {
                log.info("vertx server start listen on port:{}", severConf.getPort());
            } else {
                log.error("failed to listen:{}", result.cause().getMessage());
            }
        });

        vertx.setPeriodic(5000L, trace -> {
            vertx.executeBlocking(this::doBusinessForBlock, asyncResult -> {
                if (asyncResult.succeeded()) {
                    log.info("block task async exec successfully.");
                } else {
                    log.info("block task async exec failed.");
                }
            });
        });
    }

    private HttpServerOptions createServerOptions() {
        HttpServerOptions serverOptions = new HttpServerOptions();
        serverOptions.setCompressionSupported(true)
                .setDecompressionSupported(true)
                .setLogActivity(true)
                .setHandle100ContinueAutomatically(true)
                .setHost(severConf.getHosts())
                .setPort(severConf.getPort())
                .setIdleTimeout(severConf.getIdleTimeout())
                .setSsl(false);
        return serverOptions;
    }

    private void doBusinessForBlock(Promise<String> promise) {
        log.info("vertx server timer block task");
        try {
            Thread.currentThread().sleep(2000L);
            promise.complete("block task exec succefully.");
        } catch (InterruptedException ex) {
            log.error("block task exec failed:{}", ex.getMessage());
            promise.fail("block task exec failed");
        }
    }

    private void registerGetReq(Router router) {
        router.get("/hi/v1").handler(routingContext -> {
            log.info("enter into /hi/v1");
            HttpServerResponse resp = routingContext.response();
            resp.setStatusCode(HttpResponseStatus.OK.code())
                    .end("get request hi-v1 ok.");
            resp.exceptionHandler(ex -> {
                log.error("get request error:{}", ex);
            });
        });
    }

    private void registerPostReq(Router router) {
        router.post("/hi/v2").handler(routingContext -> {
            log.info("enter into /hi/v2");
            HttpServerResponse resp = routingContext.response();
            resp.setStatusCode(HttpResponseStatus.OK.code())
                    .end("post request hi-v2 ok.");
            resp.exceptionHandler(ex -> {
                log.error("post request error:{}", ex);
            });
        });
    }

    private void processInvalidPath(Router router) {
        router.route().handler(routingContext -> {
            Optional.ofNullable(routingContext).map(RoutingContext::request).ifPresent(request -> {
                log.error("unknown path:{}", request.path());
                request.response()
                        .setChunked(true)
                        .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
                        .write("unknown path:" + request.path())
                        .end();
                request.resume();
            });
        });
    }
}
