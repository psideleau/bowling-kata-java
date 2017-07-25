package com.surveysampling.bowling.spring;

import org.springframework.http.server.reactive.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;
import reactor.core.publisher.*;

/**
 * Created by SSI.
 */
@Component
public class CORSFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();

        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        response.getHeaders().add("Access-Control-Allow-Headers", "*");

        return chain.filter(exchange);
    }
}
