package com.niw.backend.repository;

import com.niw.backend.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("testUser");
        userEntity.setEmail("testUser@example.com");
        entityManager.persistAndFlush(userEntity);
    }

    @Test
    @DisplayName("Find user by name")
    void findUserByName() {
        Optional<UserEntity> foundUser = userRepository.findByName("testUser");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("testUser");
    }

    @Test
    @DisplayName("Find user by non-existing name")
    void findUserByNonExistingName() {
        Optional<UserEntity> foundUser = userRepository.findByName("nonExistingUser");
        assertThat(foundUser).isNotPresent();
    }

    @Test
    @DisplayName("Check if user exists by name")
    void checkIfUserExistsByName() {
        Boolean exists = userRepository.existsByName("testUser");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Check if user does not exist by name")
    void checkIfUserDoesNotExistByName() {
        Boolean exists = userRepository.existsByName("nonExistingUser");
        assertThat(exists).isFalse();
    }
}