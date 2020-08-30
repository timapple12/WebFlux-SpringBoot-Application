package com.example.webFlux.controllers;

import com.example.webFlux.domain.User;
import com.example.webFlux.repositories.UserRepository;
import com.example.webFlux.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class UserController {

    private final UserRepository repository;
    private final JwtUtil jwtUtil;
    private static final ResponseEntity<Object> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    public UserController(UserRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange serverWebExchange) {
        return serverWebExchange.getFormData().flatMap(credentials ->
                repository.findByUsername(credentials.getFirst("username"))
                        .cast(User.class)
                        .map(userDetails ->
                                        credentials.getFirst("password").equals(
                                        userDetails.getPassword())
                                            ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                            : UNAUTHORIZED
                        )
                .defaultIfEmpty(UNAUTHORIZED)
        );

    }

}
