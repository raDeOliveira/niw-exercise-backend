package com.niw.backend.service;


import com.niw.backend.dto.UserDTO;
import com.niw.backend.payload.response.UserResponse;
import org.springframework.stereotype.Component;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 8:08 PM
 **/

@Component
public interface PaymentService {

    UserResponse calculatePayment(UserDTO userDto);
}
