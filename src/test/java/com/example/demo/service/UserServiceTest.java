package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static reactor.core.publisher.Mono.when;

public class UserServiceTest {
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private UserService userService = new UserService(userRepository);

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");
    }

    @Test
    void testGetUserById() {
        // Given
        Long userId = 1L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Mono.just(testUser));
        // when
        Mono<User> userMono = userService.getUserById(userId);
        // then
        StepVerifier.create(userMono)
                .expectNextMatches(u -> u.getName().equals("John Doe"))
                .verifyComplete();

    }

    @Test
    void testGetUserByIdUserNotFound() {
        // Given
        Long userId = 888L;
        Mockito.when(userRepository.findById(userId)).thenReturn(Mono.empty());
        // when
        Mono<User> userMono = userService.getUserById(userId);
        // then
        StepVerifier.create(userMono)
                .expectNextCount(0)
                .verifyComplete();
    }
}
