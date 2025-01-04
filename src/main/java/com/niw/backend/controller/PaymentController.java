package com.niw.backend.controller;

import com.niw.backend.dto.UserDTO;
import com.niw.backend.mapper.UserMapper;
import com.niw.backend.payload.request.UserRequest;
import com.niw.backend.payload.response.UserResponse;
import com.niw.backend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 9:14 PM
 **/

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserMapper userMapper;

    public PaymentController(PaymentService paymentService, UserMapper userMapper) {
        this.paymentService = paymentService;
        this.userMapper = userMapper;
    }

    @PostMapping("/calculate")
    public ResponseEntity<UserResponse> calculatePayment
            (
                    @RequestBody UserRequest userRequest
            ) {

        UserDTO user = userMapper.fromRequest(userRequest);

        return new ResponseEntity<>(paymentService.calculatePayment(user), HttpStatus.OK);
    }

    // NOTE - add endpoint to save payment
}
