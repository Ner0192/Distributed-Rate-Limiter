package com.example.crud_reac;

import reactor.core.publisher.Mono;

public interface UserService {

    public Mono<String> delUser(String id);

    public Mono<User> addUser(User user);

    public Mono<User> updateUser(User user);
}
