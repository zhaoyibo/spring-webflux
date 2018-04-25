package com.windmt.webflux.functional;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author: zhaoyibochn@gmail.com
 * @create: 2018-04-25
 **/
@Configuration
public class SimpleCalculator {

    HandlerFunction<ServerResponse> add = request -> ServerResponse.ok()
            .body(Mono.just(parseOperand(request, "v1") + parseOperand(request, "v2")), Integer.class);

    HandlerFunction<ServerResponse> subtract = request -> ServerResponse.ok()
            .body(Mono.just(parseOperand(request, "v1") - parseOperand(request, "v2")), Integer.class);

    HandlerFunction<ServerResponse> multiply = request -> ServerResponse.ok()
            .body(Mono.just(parseOperand(request, "v1") * parseOperand(request, "v2")), Integer.class);

    HandlerFunction<ServerResponse> divide = request -> ServerResponse.ok()
            .body(Mono.just(parseOperand(request, "v1") / parseOperand(request, "v2")), Integer.class);


    private int parseOperand(final ServerRequest request, final String param) {
        try {
            return Integer.parseInt(request.queryParam(param).orElse("0"));
        } catch (final NumberFormatException e) {
            return 0;
        }
    }

//    @Bean
//    public RouterFunction<ServerResponse> routerFunction() {
//        return RouterFunctions.route(RequestPredicates.GET("/add"), add)
//                .andRoute(RequestPredicates.GET("/subtract"), subtract)
//                .andRoute(RequestPredicates.GET("/multiply"), multiply)
//                .andRoute(RequestPredicates.GET("/divide"), divide);
//    }

}
