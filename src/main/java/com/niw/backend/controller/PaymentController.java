package com.niw.backend.controller;

import com.niw.backend.dto.PaymentDTO;
import com.niw.backend.dto.UserDTO;
import com.niw.backend.mapper.PaymentMapper;
import com.niw.backend.mapper.UserMapper;
import com.niw.backend.payload.request.PaymentRequest;
import com.niw.backend.payload.request.UserRequest;
import com.niw.backend.payload.response.PaymentResponse;
import com.niw.backend.payload.response.UserResponse;
import com.niw.backend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 9:14 PM
 **/

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;
    private final UserMapper userMapper;

    public PaymentController(PaymentService paymentService, PaymentMapper paymentMapper, UserMapper userMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
        this.userMapper = userMapper;
    }

    @PostMapping("/calculate")
    public ResponseEntity<PaymentResponse> calculatePayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentDTO payment = paymentMapper.fromRequest(paymentRequest);
        return new ResponseEntity<>(paymentService.calculatePayment(payment), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponse> savePayment(@RequestBody UserRequest userRequest) throws IOException {
        UserDTO user = userMapper.fromRequest(userRequest);
        return new ResponseEntity<>(paymentService.saveAndExportData(user), HttpStatus.OK);
    }

    // NOTE - add endpoint to save payment
}
