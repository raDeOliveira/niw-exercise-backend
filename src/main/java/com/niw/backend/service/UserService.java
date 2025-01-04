package com.niw.backend.service;

import com.niw.backend.dto.UserDTO;
import org.springframework.stereotype.Component;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:32 PM
 **/

@Component
public interface UserService {

    UserDTO createUser(UserDTO userDto);
    UserDTO getUserById(Integer id);
    UserDTO updateUser(Integer id, UserDTO userDto);

}
