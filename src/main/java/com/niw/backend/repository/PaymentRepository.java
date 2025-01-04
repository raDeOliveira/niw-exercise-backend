package com.niw.backend.repository;

import com.niw.backend.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:31 PM
 **/

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
}
