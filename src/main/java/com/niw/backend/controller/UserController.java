package com.niw.backend.controller;

import com.niw.backend.dto.UserDTO;
import com.niw.backend.mapper.UserMapper;
import com.niw.backend.payload.request.UserRequest;
import com.niw.backend.payload.response.UserResponse;
import com.niw.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:38 PM
 **/

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserDTO user = userMapper.fromRequest(userRequest);
        UserDTO response = userService.createUser(user);

        return new ResponseEntity<>(userMapper.toResponse(response), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        UserDTO user = userService.getUserById(id);
        UserResponse response = userMapper.toResponse(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        UserDTO user = userMapper.fromRequest(userRequest);
        UserDTO response = userService.updateUser(id, user);

        return new ResponseEntity<>(userMapper.toResponse(response), HttpStatus.OK);
    }
}
