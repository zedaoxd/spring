package br.com.bruno.userserviceapi.service;

import org.springframework.stereotype.Service;

import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.mapper.UserMapper;
import br.com.bruno.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.responses.UserResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(String id) {
        final User user = userRepository.findById(id).orElseThrow();
        return userMapper.fromEntity(user);
    }

}
