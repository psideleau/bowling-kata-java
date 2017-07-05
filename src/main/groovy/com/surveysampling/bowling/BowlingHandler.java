package com.surveysampling.bowling;

import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;
import reactor.core.scheduler.*;

import java.util.*;
import java.util.concurrent.*;

@Service
public class BowlingHandler {
    private static final Scheduler SCHEDULER = Schedulers.fromExecutor(Executors.newCachedThreadPool());
    public Mono<ServerResponse> get(ServerRequest request) {
        System.out.println("Handler Running on Thread " + Thread.currentThread().getId());

        return Mono.fromCallable(() -> {
            Lane lane1 = new Lane();
            lane1.setBowlers(Arrays.asList(new Bowler("paul")));
            System.out.println("Running on Thread " + Thread.currentThread().getId());
            return lane1;
        }).subscribeOn(SCHEDULER)
            .as((Mono<Lane> str) -> {
            System.out.println("Streamming Running on Thread " + Thread.currentThread().getId());

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(str, Lane.class);
        });

    }
}

