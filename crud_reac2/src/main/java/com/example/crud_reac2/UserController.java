package com.example.crud_reac2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

        @Autowired
        private UserService userService;

        @GetMapping("/getAllUser")
        public Flux<User> getAllUser() {

                return userService.getAllUser();
        }

        @GetMapping("/getUserById/{id}")
        public Mono<User> getUserById(@PathVariable String id) {
                return userService.getUserById(id);
        }
}

