package com.example.webFlux.config;

import com.example.webFlux.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;

    public AuthManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username;

        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            username = null;
            System.err.println(e);
        }

        if(username != null && jwtUtil.validateToken(token)){
            Claims claims = jwtUtil.getBodyFromToken(token);
            List<String> roles = claims.get("role", List.class);
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            return Mono.just(authenticationToken);
        }else {
            return Mono.empty();
        }
    }

}
