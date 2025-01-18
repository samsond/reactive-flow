package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@WebFluxTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setup() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");
    }

    @Test
    void testGetUser() {
        // mock the service method
        when(userService.getUserById(1L)).thenReturn(Mono.just(testUser));

        // perform the Get request
        webTestClient.get().uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> {
                    assertEquals(1L, user.getId());
                    assertEquals("John Doe", user.getName());
                    assertEquals("john.doe@example.com", user.getEmail());
                });
    }

    @Test
    void testUserNotFound() {
        // mock the service method to return an empty Mono for non-existent user
        when(userService.getUserById(888L)).thenReturn(Mono.empty());

        // perform the Get request and assert the response
        webTestClient.get().uri("/users/888")
                .exchange()
                .expectStatus().isNotFound();

    }
}
