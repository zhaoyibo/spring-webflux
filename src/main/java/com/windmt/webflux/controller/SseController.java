package com.windmt.webflux.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description:
 * @author: zhaoyibochn@gmail.com
 * @create: 2018-04-09 22:39
 **/
@RestController
@RequestMapping("/sse")
public class SseController {

    @GetMapping("/random")
    public Flux<ServerSentEvent<Integer>> random() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(seq.toString())
                        .data(ThreadLocalRandom.current().nextInt())
                        .build()
                );
    }

}
