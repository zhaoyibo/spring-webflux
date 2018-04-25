package com.windmt.webflux;

import com.windmt.webflux.model.User;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: zhaoyibochn@gmail.com
 * @create: 2018-04-25
 **/
public class WebClientTests {

    @Test
    public void testRESTClient() throws InterruptedException {
        User user = new User(4, "赵六", "z6@qq.com");
        WebClient client = WebClient.create("http://localhost:8080/user"); // 1
        Flux<User> createdUser = client.post() // 2
                .uri("") // 3
                .accept(MediaType.APPLICATION_JSON) // 4
                .body(Mono.just(user), User.class) // 5
                .retrieve() // 6
                .bodyToFlux(User.class); // 7

        createdUser.subscribe(System.out::println); // 8
        TimeUnit.SECONDS.sleep(1); // 9
    }

    @Test
    public void testSEEClient1() {
        final WebClient client = WebClient.create();
        client.get()
                .uri("http://localhost:8080/sse/random")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(response ->
                        response.body(
                                BodyExtractors.toFlux(
                                        new ParameterizedTypeReference<ServerSentEvent<String>>() {
                                        }
                                )
                        )
                )
                .filter(sse -> Objects.nonNull(sse.data()))
                .map(ServerSentEvent::data)
                .buffer(10)
                .doOnNext(System.out::println)
                .blockFirst();
    }

    @Test
    public void testSEEClient2() {
        WebClient client = WebClient.create();
        client.get()
                .uri("http://localhost:8080/sse/random")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .log()
                .take(10)
                .blockLast();
    }

    @Test
    public void testWSClient() {
        WebSocketClient client = new ReactorNettyWebSocketClient(); // 1
        client.execute(URI.create("ws://localhost:8080/echo"), session -> // 2
                session.send(Flux.just(session.textMessage("Hello"))) // 3
                        .thenMany(session.receive().take(1).map(WebSocketMessage::getPayloadAsText)) // 4
                        .doOnNext(System.out::println)
                        .then())
                .block(Duration.ofMillis(5000));
    }

}
