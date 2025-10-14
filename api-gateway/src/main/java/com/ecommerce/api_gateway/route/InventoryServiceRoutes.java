package com.ecommerce.api_gateway.route;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class InventoryServiceRoutes {

    @Bean
    public RouterFunction<ServerResponse> inventoryRoutes() {
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.POST("/api/v1/inventory/products"),
                        HandlerFunctions.http("http://localhost:8080/api/v1/inventory/products"))
                .route(RequestPredicates.path("/inventory/product/{productId}"),
                        request -> forwardWithPathVariable(request, "productId",
                                "http://localhost:8080/api/v1/inventory/product"))
                .route(RequestPredicates.path("/inventory/product/{productId}"),
                        request -> forwardWithPathVariable(request, "productId",
                                "http://localhost:8080/api/v1/inventory/product"))
                .build();
    }

    private static ServerResponse forwardWithPathVariable(ServerRequest request,
                                                          String pathVariableName,
                                                          String targetBaseUrl) throws Exception {
        String value  = request.pathVariable(pathVariableName);
        return HandlerFunctions.http(targetBaseUrl + value).handle(request);
    }
}
