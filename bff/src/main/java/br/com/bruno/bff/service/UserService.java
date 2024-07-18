package br.com.bruno.bff.service;

import br.com.bruno.bff.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserFeignClient userFeignClient;

    public UserResponse findById(String id) {
        return userFeignClient.findById(id).getBody();
    }

    public void save(CreateUserRequest request) {
        userFeignClient.save(request);
    }

    public List<UserResponse> findAll() {
        return userFeignClient.findAll().getBody();
    }

    public UserResponse update(String id, UpdateUserRequest updateUserRequest) {
        return userFeignClient.update(id, updateUserRequest).getBody();
    }
}
