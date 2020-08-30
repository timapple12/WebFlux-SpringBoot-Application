package com.example.webFlux.config;

import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthManager authManager;

    public SecurityContextRepository(AuthManager authManager) {
        this.authManager = authManager;
    }

    @SneakyThrows
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new NoSuchMethodException("Not implemented yet");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String header = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        if(header != null && header.startsWith("Bearer ")){
            String authToken = header.substring(7);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
            return  authManager.authenticate(auth)
                        .map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
