package com.example.crud_reac2;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Value("${app.req.delay}")
    private int delay;

    @Override
    public Flux<User> getAllUser() {
        return userRepo.findAll().delaySubscription(Duration.ofSeconds(delay));
    }

    @Override
    public Mono<User> getUserById(String id) {
        return userRepo.findById(id).delaySubscription(Duration.ofSeconds(delay));
    }

}
