package com.niw.backend.repository;

import com.niw.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:30 PM
 **/

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);

}
