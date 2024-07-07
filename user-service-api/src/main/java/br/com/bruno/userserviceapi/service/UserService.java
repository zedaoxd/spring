package br.com.bruno.userserviceapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.mapper.UserMapper;
import br.com.bruno.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(String id) {
        final User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Object not found. Id: " + id + ", Type: " + UserResponse.class.getSimpleName()
            ));
        return userMapper.fromEntity(user);
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailExists(request.email(), null);
        final User user = userMapper.toRequest(request);
        userRepository.save(user);
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
