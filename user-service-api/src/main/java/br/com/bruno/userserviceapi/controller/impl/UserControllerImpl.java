package br.com.bruno.userserviceapi.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.userserviceapi.controller.UserController;
import br.com.bruno.userserviceapi.entity.User;
import br.com.bruno.userserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<User> findById(String id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    
}
