package com.niw.backend.service;

import com.niw.backend.dto.PaymentDTO;
import com.niw.backend.dto.UserDTO;
import com.niw.backend.entity.UserEntity;
import com.niw.backend.mapper.UserMapper;
import com.niw.backend.repository.UserRepository;
import com.niw.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get user by ID successfully")
    void getUserByIdSuccessfully() {
        UserDTO userDTO = new UserDTO(
                "testUser",
                "test@email.com",
                new PaymentDTO(
                        48,
                        "INTERNAL",
                        7555,
                        218.26f));
        UserEntity userEntity = new UserEntity();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(any(UserEntity.class))).thenReturn(userDTO);

        UserDTO foundUser = userServiceImpl.getUserById(1);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.name()).isEqualTo("testUser");
        assertThat(foundUser.email()).isEqualTo("test@email.com");
    }

    @Test
    @DisplayName("Get user by non-existing ID")
    void getUserByNonExistingId() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userServiceImpl.getUserById(1));
    }
}
