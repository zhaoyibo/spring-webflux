package com.windmt.webflux.controller;

import com.windmt.webflux.exception.ResourceNotFoundException;
import com.windmt.webflux.model.User;
import com.windmt.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @description:
 * @author: zhaoyibochn@gmail.com
 * @create: 2018-04-09 22:25
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
    public void notFound() {
    }

    @GetMapping("")
    public Flux<User> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping("")
    public Flux<User> create(@RequestBody Flux<User> users) {
        return userService.createOrUpdate(users);
    }

    @PutMapping("/{id}")
    public Mono<User> update(@PathVariable Integer id, @RequestBody User user) {
        Objects.requireNonNull(user);
        user.setId(id);
        return userService.createOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public Mono<User> delete(@PathVariable Integer id) {
        return userService.delete(id);
    }


}
