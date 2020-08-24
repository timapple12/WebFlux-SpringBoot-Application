package com.example.webFlux.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MainPageHandler  {

    public Mono<ServerResponse> sayHello(ServerRequest request){

        return (Mono<ServerResponse>) ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hi, you are p"));
    }
}
