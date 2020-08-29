package com.example.webFlux.services;

import com.example.webFlux.domain.Message;
import com.example.webFlux.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageService {
    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Flux<Message> list(){
        return repository.findAll();
    }
    public Mono<Message> addOne(Message message){
        return repository.save(message);
    }
}
