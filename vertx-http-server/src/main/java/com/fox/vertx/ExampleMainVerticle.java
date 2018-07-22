package com.fox.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


public class ExampleMainVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleMainVerticle.class);

    @Override
    public void start(Future<Void> future) throws Exception {

        /* Http server verticle */
        String verticleName = ExampleHttpServerVerticle.class.getName();

        /* Opciones del servidor http */
        DeploymentOptions options = new DeploymentOptions();
        /* Asignamos como instancias el numero de procesadores por 2 (Patrón multireactor)
         * http://vertx.io/docs/vertx-core/java/#_specifying_number_of_verticle_instances */
        options.setConfig(config());
        options.setInstances(Runtime.getRuntime().availableProcessors() * 2);
        //options.setInstances(1);

        vertx.deployVerticle(verticleName, options, ar -> {
            if (ar.succeeded()) {
                LOGGER.info(String.format("Deployment verticle %s ok ", verticleName));
                future.complete();
            } else {
                LOGGER.info(String.format("Deployment verticle %s ko ", verticleName));
                future.fail(ar.cause());
            }
        });
    }
}
