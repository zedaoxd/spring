package br.com.bruno.userserviceapi.service;

import org.springframework.stereotype.Service;

import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

}
