package com.niw.backend.controller;

import com.niw.backend.dto.PaymentDTO;
import com.niw.backend.dto.UserDTO;
import com.niw.backend.payload.request.PaymentRequest;
import com.niw.backend.payload.request.UserRequest;
import com.niw.backend.payload.response.PaymentResponse;
import com.niw.backend.payload.response.UserResponse;
import com.niw.backend.service.PaymentService;
import com.niw.backend.mapper.PaymentMapper;
import com.niw.backend.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:35 PM
 **/

class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Calculate payment successfully")
    void calculatePaymentSuccessfully() {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .financingFactor("INTERNAL")
                .monthlyPayment(12)
                .vehiclePrice(10000)
                .build();

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .financingFactor("INTERNAL")
                .monthlyPayment(48)
                .vehiclePrice(7555)
                .toPayEveryMonth(0.0f)
                .build();

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .toPayEveryMonth(BigDecimal.valueOf(24))
                .financingFactor(4.0f)
                .monthlyPayment(12)
                .vehiclePrice(2850.0f)
                .build();

        when(paymentMapper.fromRequest(any(PaymentRequest.class))).thenReturn(paymentDTO);
        when(paymentService.calculatePayment(any(PaymentDTO.class))).thenReturn(paymentResponse);

        ResponseEntity<PaymentResponse> response = paymentController.calculatePayment(paymentRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(paymentResponse);
    }

    @Test
    @DisplayName("Save payment successfully")
    void savePaymentSuccessfully() throws IOException {
        UserRequest userRequest = UserRequest.builder()
                .name("testUser")
                .email("test@email.com")
                .paymentDTO(PaymentDTO.builder()
                        .vehiclePrice(4562)
                        .financingFactor("INTERNAL")
                        .monthlyPayment(12)
                        .toPayEveryMonth(0.0f)
                        .build())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .name("testUser")
                .email("test@email.com")
                .paymentDTO(PaymentDTO.builder()
                        .vehiclePrice(2222)
                        .financingFactor("INTERNAL")
                        .monthlyPayment(12)
                        .toPayEveryMonth(0.0f)
                        .build())
                .build();


        UserResponse userResponse = UserResponse.builder()
                .name("testUser")
                .email("test@email.com")
                .paymentDTO(PaymentDTO.builder()
                        .vehiclePrice(1258)
                        .financingFactor("INTERNAL")
                        .monthlyPayment(12)
                        .toPayEveryMonth(0.0f)
                        .build())
                .build();

        when(userMapper.fromRequest(any(UserRequest.class))).thenReturn(userDTO);
        when(paymentService.saveAndExportData(any(UserDTO.class))).thenReturn(userResponse);

        ResponseEntity<UserResponse> response = paymentController.savePayment(userRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userResponse);
    }

    @Test
    @DisplayName("Calculate payment with invalid request")
    void calculatePaymentWithInvalidRequest() {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .financingFactor("NOFACTOR")
                .monthlyPayment(12)
                .vehiclePrice(-1235)
                .build();

        when(paymentMapper.fromRequest(any(PaymentRequest.class))).thenReturn(null);

        ResponseEntity<PaymentResponse> response = paymentController.calculatePayment(paymentRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Save payment with invalid request")
    void savePaymentWithInvalidRequest() throws IOException {
        UserRequest userRequest = UserRequest.builder()
                .name("testUser")
                .email("test@email.com")
                .paymentDTO(PaymentDTO.builder()
                        .vehiclePrice(-32)
                        .financingFactor("NOFACTOR")
                        .monthlyPayment(12)
                        .toPayEveryMonth(0.0f)
                        .build())
                .build();
        when(userMapper.fromRequest(any(UserRequest.class))).thenReturn(null);

        ResponseEntity<UserResponse> response = paymentController.savePayment(userRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
