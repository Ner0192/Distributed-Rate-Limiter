package com.example.crud_reac;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Value("${app.req.delay}")
    private int delay;

    @Override
    public Mono<String> delUser(String id) {
        return userRepo.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return userRepo.deleteById(id)
                                .then(Mono.just("Deleted Successfully!").delaySubscription(Duration.ofSeconds(delay)));
                    } else {
                        return Mono.just("User not Found!");
                    }
                });
    }

    @Override
    public Mono<User> addUser(User user) {
        return userRepo.save(user).delaySubscription(Duration.ofSeconds(delay));
    }

    @Override
    public Mono<User> updateUser(User user) {
        return userRepo.save(user).delaySubscription(Duration.ofSeconds(delay));
    }

}
