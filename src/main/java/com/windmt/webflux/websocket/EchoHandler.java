package com.windmt.webflux.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @author: zhaoyibochn@gmail.com
 * @create: 2018-04-09 22:47
 **/
@Component
public class EchoHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                session.receive()
                        .map(msg -> session.textMessage("Echo -> " + msg.getPayloadAsText())));
    }
}
