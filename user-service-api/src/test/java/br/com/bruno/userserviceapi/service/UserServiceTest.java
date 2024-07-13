package br.com.bruno.userserviceapi.service;

import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.mapper.UserMapper;
import br.com.bruno.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.bruno.userserviceapi.creator.CreatorUtils.generateMock;
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
        when(userMapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

        final var response = userService.findById(id);

        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());
        verify(userRepository).findById(anyString());
        verify(userMapper).fromEntity(any(User.class));
    }

    @Test
    void whenCallFindByIdWithInvalidIdThenThrowResourceNotFoundException() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(id));
        verify(userRepository).findById(anyString());
        verify(userMapper, never()).fromEntity(any(User.class));
    }

    @Test
    void whenCallFindAllThenReturnListOfUserResponse() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        when(userMapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

        final var response = userService.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.get(0).getClass());
        verify(userRepository).findAll();
        verify(userMapper, times(2)).fromEntity(any(User.class));
    }

    @Test
    void whenCallSaveThenSuccess() {
        final var request = generateMock(CreateUserRequest.class);
        when(userMapper.fromRequest(any())).thenReturn(new User());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.save(request);

        verify(userMapper).fromRequest(request);
        verify(encoder).encode(request.password());
        verify(userRepository).save(any(User.class));
        verify(userRepository).findByEmail(request.email());
    }
}