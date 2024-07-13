package br.com.bruno.userserviceapi.service;

import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.mapper.UserMapper;
import br.com.bruno.userserviceapi.repository.UserRepository;
import models.responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    private String id;

    @BeforeEach
    void setUp() {
        id = "1";
    }

    @Test
    void whenCallFindByIdWithValidIdThenReturnUserResponse() {
        when(userRepository.findById(anyString())).thenReturn(Optional.of(new User()));
        when(userMapper.fromEntity(any(User.class))).thenReturn(mock(UserResponse.class));

        final var response = userService.findById(id);

        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());
        verify(userRepository, times(1)).findById(anyString());
        verify(userMapper, times(1)).fromEntity(any(User.class));
    }
}