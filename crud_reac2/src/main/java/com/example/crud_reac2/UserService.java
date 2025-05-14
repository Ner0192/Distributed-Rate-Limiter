package com.example.crud_reac2;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    public Flux<User> getAllUser();

    public Mono<User> getUserById(String id);
}
