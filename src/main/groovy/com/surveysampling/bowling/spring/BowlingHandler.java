package com.surveysampling.bowling.spring;

import com.surveysampling.bowling.*;
import org.springframework.http.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;
import reactor.core.scheduler.*;

import java.util.concurrent.*;

public class BowlingHandler {
    private final BowlingService bowlingService;
    private final Scheduler businessLogicScheduler;

    public BowlingHandler(BowlingService bowlingService, Scheduler businessLogicScheduler) {
        this.bowlingService = bowlingService;
        this.businessLogicScheduler = businessLogicScheduler;
    }

    public Mono<ServerResponse> start(ServerRequest request) {
        return executeAysnc(bowlingService::startGame);
    }

    private Mono<ServerResponse> executeAysnc(Callable<GameResponse> businessLogic) {
        return Mono
                .fromCallable(businessLogic)
                .subscribeOn(businessLogicScheduler)
                .as((response) ->
                        ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(response, GameResponse.class));
    }

    public Mono<ServerResponse> rollPins(ServerRequest request) {

        return executeAysnc(() -> rollPinsInBackgroundThread(request));
    }

    private GameResponse rollPinsInBackgroundThread(ServerRequest request) {
        GameRollRequest rollRequest = GameRollRequest.builder()
                .gameID(request.pathVariable("gameId"))
                .pins(Integer.parseInt(request.queryParam("pins").get()))
                .build();
        return bowlingService.rollPins(rollRequest);
    }
}