package com.example.webFlux.controllers;

import com.example.webFlux.domain.Message;
import com.example.webFlux.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/main")
public class MainController {
    private final MessageService service;

    @Autowired
    public MainController(MessageService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Message> getData(){
        return service.list();
    }

    @PostMapping
    public Mono<Message> save(@RequestBody Message message){
        return service.addOne(message);
    }

}
