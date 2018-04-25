package com.windmt.webflux.service;

import com.windmt.webflux.exception.ResourceNotFoundException;
import com.windmt.webflux.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: zhaoyibochn@gmail.com
 * @create: 2018-04-09 22:06
 **/
@Service
public class UserService {

    private final Map<Integer, User> data = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        data.put(1, new User(1, "张三", "z3@qq.com"));
        data.put(2, new User(2, "李四", "l4@qq.com"));
        data.put(3, new User(3, "王五", "w5@qq.com"));
    }


    public Flux<User> list() {
        return Flux.fromIterable(this.data.values());
    }

    public Flux<User> getByIds(Flux<Integer> ids) {
        return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
    }

    public Mono<User> getById(Integer id) {
        return Mono.justOrEmpty(this.data.get(id))
                .switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

    public Flux<User> createOrUpdate(Flux<User> users) {
        return users.doOnNext(user -> this.data.put(user.getId(), user));
    }

    public Mono<User> createOrUpdate(User user) {
        this.data.put(user.getId(), user);
        return Mono.just(user);
    }

    public Mono<User> delete(Integer id) {
        return Mono.justOrEmpty(this.data.remove(id));
    }

}
