package com.surveysampling.bowling;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.web.reactive.function.server.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
/**
 * Created by SSI.
 */
@Configuration
public class ApplicationRoutes {

    @Bean
    public RouterFunction<ServerResponse> routes(BowlingHandler bowlingHandler) {
        return nest(path("/games"),
                nest(accept(MediaType.APPLICATION_JSON),
                        route(GET("/{id}"), bowlingHandler::get)
                ));
    }
}
