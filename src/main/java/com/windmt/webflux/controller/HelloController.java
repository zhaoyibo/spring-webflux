package com.windmt.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: zhaoyibochn@gmail.com
 * @create: 2018-04-09 22:00
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Mono<String> sayHello(){
        return Mono.just("Hello World");
    }

}
