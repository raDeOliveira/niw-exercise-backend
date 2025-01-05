package com.niw.backend.repository;

import com.niw.backend.entity.PaymentEntity;
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
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private PaymentEntity paymentEntity;

    @BeforeEach
    void setUp() {
        paymentEntity = new PaymentEntity();
        paymentEntity.setVehiclePrice(7555);
        paymentEntity.setMonthlyPayment(46);
        paymentEntity.setFinancingFactor("INTERNAL");
        entityManager.persistAndFlush(paymentEntity);
    }

    @Test
    @DisplayName("Find payment by ID")
    void findPaymentById() {
        Optional<PaymentEntity> foundPayment = paymentRepository.findById(paymentEntity.getId());
        assertThat(foundPayment).isPresent();
        assertThat(foundPayment.get().getVehiclePrice()).isEqualTo(7555);
    }

    @Test
    @DisplayName("Find payment by non-existing ID")
    void findPaymentByNonExistingId() {
        Optional<PaymentEntity> foundPayment = paymentRepository.findById(-1);
        assertThat(foundPayment).isNotPresent();
    }

    @Test
    @DisplayName("Save new payment")
    void saveNewPayment() {
        PaymentEntity newPayment = new PaymentEntity();
        newPayment.setVehiclePrice(2000);
        newPayment.setMonthlyPayment(12);
        newPayment.setFinancingFactor("EXTERNAL");
        PaymentEntity savedPayment = paymentRepository.save(newPayment);
        assertThat(savedPayment.getVehiclePrice()).isEqualTo(2000);
        assertThat(savedPayment.getMonthlyPayment()).isEqualTo(12);
        assertThat(savedPayment.getFinancingFactor()).isEqualTo("EXTERNAL");
    }
}