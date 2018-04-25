package com.windmt.webflux;

import com.windmt.webflux.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * @author: zhaoyibochn@gmail.com
 * @create: 2018-04-25
 **/
public class UserControllerTest {

    private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    @Test
    public void testCreateUser() throws Exception {
        User user = new User(5, "钱七",  "q7@qq.com");
        client.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name")
                .isEqualTo("钱七");
    }

}
