package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> getUserById(Long id) {
        return Mono.defer(() -> userRepository.findById(id)).switchIfEmpty(Mono.empty());
    }


}
