package com.tgl.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

/**
 * BaseVerticle
 *
 * @author tgl
 * @since 2021/8/13
 */
public class BaseVerticle extends AbstractVerticle {
    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
    }

    @Override
    public void start() {
        Router router = Router.router(vertx);
        serverManage(router);
    }

    protected void serverManage(Router router) {
    }
}
