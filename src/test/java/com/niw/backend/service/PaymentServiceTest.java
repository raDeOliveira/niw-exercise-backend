package com.niw.backend.service;

import com.niw.backend.Properties.FactorProperties;
import com.niw.backend.dto.PaymentDTO;
import com.niw.backend.dto.UserDTO;
import com.niw.backend.entity.PaymentEntity;
import com.niw.backend.entity.UserEntity;
import com.niw.backend.mapper.PaymentMapper;
import com.niw.backend.mapper.UserMapper;
import com.niw.backend.payload.response.PaymentResponse;
import com.niw.backend.payload.response.UserResponse;
import com.niw.backend.repository.PaymentRepository;
import com.niw.backend.repository.UserRepository;
import com.niw.backend.service.impl.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

@Slf4j
class PaymentServiceTest {


    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private FactorProperties factorProperties;

    @InjectMocks
    private PaymentServiceImpl paymentServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Calculate payment with internal financing factor")
    void calculatePaymentWithInternalFinancingFactor() {
        PaymentDTO paymentDTO = new PaymentDTO(
                7555,
                "INTERNAL",
                36,
                0
        );
        when(factorProperties.getInternal()).thenReturn(4);
        when(paymentMapper.toEntity(any(PaymentDTO.class))).thenReturn(new PaymentEntity());
        when(paymentMapper.toDto(any(PaymentEntity.class))).thenReturn(paymentDTO);

        PaymentResponse response = paymentServiceImpl.calculatePayment(paymentDTO);

        log.info("response: {}", response);

        assertThat(response).isNotNull();
        assertThat(response.toPayEveryMonth()).isEqualTo(String.format("%.2f", 218.26));
    }

    @Test
    @DisplayName("Calculate payment with external financing factor")
    void calculatePaymentWithExternalFinancingFactor() {
        PaymentDTO paymentDTO = new PaymentDTO(
                7555,
                "EXTERNAL",
                24,
                0
        );
        when(factorProperties.getExternal()).thenReturn(7.0f);
        when(paymentMapper.toEntity(any(PaymentDTO.class))).thenReturn(new PaymentEntity());
        when(paymentMapper.toDto(any(PaymentEntity.class))).thenReturn(paymentDTO);

        PaymentResponse response = paymentServiceImpl.calculatePayment(paymentDTO);

        assertThat(response).isNotNull();
        assertThat(response.financingFactor()).isEqualTo(7.0f);
        assertThat(response.toPayEveryMonth()).isEqualTo(String.format("%.2f", 336.83));
    }

    @Test
    @DisplayName("Calculate payment with invalid financing factor")
    void calculatePaymentWithInvalidFinancingFactor() {
        PaymentDTO paymentDTO = new PaymentDTO(
                10000,
                "NOFACTOR",
                12,
                0
        );
        PaymentResponse response = paymentServiceImpl.calculatePayment(paymentDTO);

        assertThat(response).isNull();
    }

    @Test
    @DisplayName("Save and export user data")
    void saveAndExportUserData() throws IOException {
        UserDTO userDTO = new UserDTO(
                "testUser",
                "test@email.com",
                new PaymentDTO(
                        48,
                        "INTERNAL",
                        7555,
                        218.26f));
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(1);
        when(paymentMapper.toEntity(any(PaymentDTO.class))).thenReturn(paymentEntity);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(new UserEntity());

        UserResponse response = paymentServiceImpl.saveAndExportData(userDTO);

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("testUser");
        assertThat(response.email()).isEqualTo("test@email.com");
    }

    @Test
    @DisplayName("Cannot calculate negative number")
    void calculatePaymentWithNegativeNumber() {
        PaymentDTO paymentDTO = new PaymentDTO(
                -7555,
                "INTERNAL",
                36,
                0
        );
        when(factorProperties.getInternal()).thenReturn(4);
        when(paymentMapper.toEntity(any(PaymentDTO.class))).thenReturn(new PaymentEntity());
        when(paymentMapper.toDto(any(PaymentEntity.class))).thenReturn(paymentDTO);

        PaymentResponse response = paymentServiceImpl.calculatePayment(paymentDTO);

        assertThat(response).isNull();
    }
}