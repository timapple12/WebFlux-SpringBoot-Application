package com.example.webFlux.config;

import com.example.webFlux.handlers.MainPageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class MainPageUtilConfigRouter {

    @Bean
    public RouterFunction<ServerResponse> route(MainPageHandler handler){
        RequestPredicate predicate = RequestPredicates
                .GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));

        return RouterFunctions.route(predicate, handler::sayHello);
    }

}
