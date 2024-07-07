package br.com.bruno.userserviceapi.service;

import models.requests.UpdateUserRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.mapper.UserMapper;
import br.com.bruno.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(String id) {
        return userMapper.fromEntity(find(id));
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailExists(request.email(), null);
        final User user = userMapper.toRequest(request);
        userRepository.save(user);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::fromEntity)
                .toList();
    }

    public UserResponse update(final String id, final UpdateUserRequest updateUserRequest) {
        final User entity = find(id);
        verifyIfEmailExists(updateUserRequest.email(), id);
        return userMapper.fromEntity(userRepository.save(userMapper.update(updateUserRequest, entity)));
    }

    private User find(final String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found. Id: " + id + ", Type: " + User.class.getSimpleName()
                ));
    }

    private void verifyIfEmailExists(final String email, final String id) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    if (!user.getId().equals(id)) {
                        throw new DataIntegrityViolationException("Email [ " + email + " ] already exists");
                    }
                });
    }
}
