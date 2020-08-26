package com.example.webFlux.config;

import com.example.webFlux.handlers.MainPageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;

import java.util.Map;

@Configuration
public class MainPageUtilConfigRouter {

    @Bean
    public RouterFunction<ServerResponse> route(MainPageHandler handler){
        RequestPredicate predicate = RequestPredicates
                .GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));


        return RouterFunctions
                .route(predicate, handler::index)
                .andRoute(
                        RequestPredicates.GET("/"), handler::index);
    }

}
