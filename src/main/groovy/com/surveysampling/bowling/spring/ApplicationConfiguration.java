package com.surveysampling.bowling.spring;

import com.surveysampling.bowling.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.scheduler.*;

import java.util.concurrent.*;

import static org.springframework.web.reactive.function.BodyInserters.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
/**
 * Created by SSI.
 */
@Configuration
public class ApplicationConfiguration {
    @Bean
    BowlingService getBowlingService() {
        return new BowlingInteractor(new MemoryBowlingAccess());
    }

    @Bean
    BowlingHandler getBowlingHandler(@Autowired BowlingService bowlingService) {
        return new BowlingHandler(bowlingService,
                                    Schedulers.fromExecutor(Executors.newCachedThreadPool()));
    }

    @Bean
    public RouterFunction<ServerResponse> routes(@Autowired BowlingHandler bowlingHandler) {
        return  route(RequestPredicates.path("/hello-world"),
                        request -> ServerResponse.ok().body(fromObject("hello")))
                .andNest(path("/games"),
                    route(method(HttpMethod.PUT), bowlingHandler::start)
                    .andRoute(POST("/{gameId}"), bowlingHandler::rollPins));
    }
}
