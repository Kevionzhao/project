package com.zhao.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Admin
 */
@Component
public class TimeHandler {
    public Mono<ServerResponse> getTime(ServerRequest serverRequest){
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Now is"+ LocalDateTime.now()),String.class);
    }
    public Mono<ServerResponse> getDate(ServerRequest serverRequest){
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just("Today is "+ LocalDate.now()),String.class);
    }
    public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                Flux.interval(Duration.ofSeconds(1)).
                        map(l -> new SimpleDateFormat("HH:mm:ss").format(new Date())),
                String.class);
    }
}
