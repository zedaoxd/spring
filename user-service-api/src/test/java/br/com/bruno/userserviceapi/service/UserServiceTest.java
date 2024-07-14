package br.com.bruno.userserviceapi.service;

import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.mapper.UserMapper;
import br.com.bruno.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
    private User entity;
    private UserResponse response;
    private CreateUserRequest createUserRequest;
    private UpdateUserRequest updateUserRequest;

    @BeforeEach
    void setUp() {
        id = generateMock(String.class);
        entity = generateMock(User.class);
        response = generateMock(UserResponse.class);
        createUserRequest = generateMock(CreateUserRequest.class);
        updateUserRequest = generateMock(UpdateUserRequest.class);
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
    void whenCallSaveWithValidDataThenSuccess() {
        when(userMapper.fromRequest(any())).thenReturn(new User());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.save(createUserRequest);

        verify(userMapper).fromRequest(createUserRequest);
        verify(encoder).encode(createUserRequest.password());
        verify(userRepository).save(any(User.class));
        verify(userRepository).findByEmail(createUserRequest.email());
    }

    @Test
    void whenCallSaveWithInvalidEmailThenThrowDataIntegrityException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        assertThrows(DataIntegrityViolationException.class, () -> userService.save(createUserRequest));
        verify(userRepository).findByEmail(createUserRequest.email());
        verify(userMapper, never()).fromRequest(createUserRequest);
        verify(encoder, never()).encode(createUserRequest.password());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void whenCallUpdateWithValidDataThenReturnSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.update(updateUserRequest, entity)).thenReturn(entity);
        when(userMapper.fromEntity(entity)).thenReturn(response);

        userService.update(id, updateUserRequest);

        verify(userRepository).findByEmail(anyString());
        verify(userRepository).findById(id);
        verify(userRepository).save(entity);
        verify(userMapper).update(updateUserRequest, entity);
        verify(userMapper).fromEntity(entity);
    }

    @Test
    void whenCallUpdateWithEmailInUseThenReturnDataIntegrityViolation() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(entity));

        assertThrows(DataIntegrityViolationException.class, () -> userService.update(id, updateUserRequest));
        verify(userRepository).findByEmail(anyString());
        verify(userRepository, never()).findById(id);
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).update(any(), any());
        verify(encoder, never()).encode(any());
    }

    @Test
    void whenCallUpdateWithInvalidIdReturnResourceNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.update(id, updateUserRequest));
        verify(userRepository).findByEmail(anyString());
        verify(userRepository).findById(id);
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).update(any(), any());
        verify(encoder, never()).encode(any());
    }
}