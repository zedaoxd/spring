package br.com.bruno.userserviceapi.controller.impl;

import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static br.com.bruno.userserviceapi.creator.CreatorUtils.generateMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {

    public static final String ENDPOINT_USERS = "/users";
    public static final String VALID_EMAIL = "any@mail.com";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User entity;
    private List<User> entities;

    @BeforeEach
    void setUp() {
        var newEntity1 = generateMock(User.class);
        var newEntity2 = generateMock(User.class);

        entities = userRepository.saveAll(List.of(newEntity1, newEntity2));
        entity = entities.get(0);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll(entities);
    }

    @Test
    void testFindByIsWithSuccess() throws Exception {
        mockMvc.perform(get("/users/{id}", entity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entity.getId()))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.profiles").isArray());
    }

    @Test
    void testFindByIsWithFailure() throws Exception {
        var id = "invalid-id";
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Object not found. Id: " + id + ", Type: " + User.class.getSimpleName()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.path").value("/users/" + id));
    }

    @Test
    void testFindAllWithSuccess() throws Exception {
        mockMvc.perform(get(ENDPOINT_USERS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty())
                .andExpect(jsonPath("$[0].profiles").isArray())
                .andExpect(jsonPath("$[1].profiles").isArray());
    }

    @Test
    void testSaveWithSuccess() throws Exception {
        final var createUserRequest = generateMock(User.class).withEmail(VALID_EMAIL);

        mockMvc.perform(post(ENDPOINT_USERS)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(createUserRequest)))
                .andExpect(status().isCreated());

        userRepository.deleteByEmail(VALID_EMAIL);
    }

    @Test
    void testSaveWithConflict() throws Exception {
        final var entity = generateMock(User.class).withEmail(VALID_EMAIL);
        final var createUserRequest = generateMock(User.class).withEmail(VALID_EMAIL);

        userRepository.save(entity);

        mockMvc.perform(post(ENDPOINT_USERS)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(createUserRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email [ " + VALID_EMAIL + " ] already exists"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(CONFLICT.value()))
                .andExpect(jsonPath("$.error").value(CONFLICT.getReasonPhrase()))
                .andExpect(jsonPath("$.path").value("/users"));

        userRepository.deleteByEmail(VALID_EMAIL);
    }

    private String toJson(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}