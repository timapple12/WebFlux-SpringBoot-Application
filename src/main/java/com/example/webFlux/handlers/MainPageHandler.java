package com.example.webFlux.handlers;

import com.example.webFlux.domain.Message;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class MainPageHandler  {

    public Mono<ServerResponse> sayHello(ServerRequest request){
        Flux<Message> messageFlux = Flux
                .just("First",
                        "Second",
                        "Third")
                .map(Message::new);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(messageFlux, Message.class);
    }

    public Mono<ServerResponse> index (ServerRequest request){
        String name = request.queryParam("user")
                .orElse("Someone Else");
        return ServerResponse
                .ok()
                .render("index", Map.of("user",name));
    }
}
