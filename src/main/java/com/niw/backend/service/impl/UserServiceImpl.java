package com.niw.backend.service.impl;

import com.niw.backend.dto.UserDTO;
import com.niw.backend.entity.UserEntity;
import com.niw.backend.exception.AlreadyExistsException;
import com.niw.backend.exception.CustomEntityNotFoundException;
import com.niw.backend.exception.DatabaseAccessException;
import com.niw.backend.mapper.UserMapper;
import com.niw.backend.repository.UserRepository;
import com.niw.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:33 PM
 **/

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    Class<?> className = UserEntity.class;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private void databaseErrorAccess(DataAccessException e) {
        log.error("Error accessing database {}", e.getMessage());
    }

    @Override
    public UserDTO createUser(UserDTO userDto) {
        Optional<UserEntity> userExist = userRepository.findByUsername(userDto.username());
        if (userExist.isPresent()) {
            throw new AlreadyExistsException(userDto.username());
        }
        UserEntity user = userMapper.toEntity(userDto);
        UserEntity userSaved = userRepository.save(user);

        return userMapper.toDto(userSaved);
    }

    @Override
    public UserDTO getUserById(Integer id) {
        try {
            return userRepository
                    .findById(id)
                    .map(userMapper::toDto)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDTO updateUser(Integer id, UserDTO userDto) {
        UserEntity userExist;
        try {
            userExist = userRepository
                    .findById(id)
                    .orElseThrow(() -> new CustomEntityNotFoundException(className.getSimpleName(), id));

            userExist.setUsername(userDto.username());
            userExist.setEmail(userDto.email());
        } catch (DataAccessException e) {
            databaseErrorAccess(e);
            throw new DatabaseAccessException(e);
        }

        UserEntity userUpdated = userRepository.save(userExist);
        return userMapper.toDto(userUpdated);
    }
}
