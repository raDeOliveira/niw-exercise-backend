package com.niw.backend.service;


import com.niw.backend.dto.PaymentDTO;
import com.niw.backend.dto.UserDTO;
import com.niw.backend.payload.response.PaymentResponse;
import com.niw.backend.payload.response.UserResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 8:08 PM
 **/

@Component
public interface PaymentService {

    UserResponse saveAndExportData(UserDTO userDto) throws IOException;
    PaymentResponse calculatePayment(PaymentDTO paymentDto);
}
