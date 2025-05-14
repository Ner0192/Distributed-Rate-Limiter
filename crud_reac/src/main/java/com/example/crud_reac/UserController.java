package com.example.crud_reac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class UserController {

        @Autowired
        private UserService userService;

        @DeleteMapping("/delUser/{id}")
        public Mono<String> delUser(@PathVariable String id) {

                return userService.delUser(id);
        }

        @PostMapping("/addUser")
        public Mono<User> addUser(@RequestBody User user) {

                return userService.addUser(user);
        }

        @PutMapping("/updateUser")
        public Mono<User> updateUser(@RequestBody User user) {

                return userService.updateUser(user);
        }

}
