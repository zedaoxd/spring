package br.com.bruno.userserviceapi.service;

import models.requests.UpdateUserRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final UserRepository repository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder;

    public UserResponse findById(String id) {
        return mapper.fromEntity(find(id));
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailExists(request.email(), null);
        final User user = mapper
                .toRequest(request)
                .withPassword(encoder.encode(request.password()));
        repository.save(user);
    }

    public List<UserResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public UserResponse update(final String id, final UpdateUserRequest request) {
        final User entity = find(id);
        verifyIfEmailExists(request.email(), id);
        return mapper.fromEntity(
                repository.save(
                        mapper.update(request, entity)
                                .withPassword(request.password() != null ? encoder.encode(request.password()) : entity.getPassword())
                )
        );
    }

    private User find(final String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found. Id: " + id + ", Type: " + User.class.getSimpleName()
                ));
    }

    private void verifyIfEmailExists(final String email, final String id) {
        repository.findByEmail(email)
                .ifPresent(user -> {
                    if (!user.getId().equals(id)) {
                        throw new DataIntegrityViolationException("Email [ " + email + " ] already exists");
                    }
                });
    }
}
