package com.niw.backend.service.impl;

import com.niw.backend.Properties.FactorProperties;
import com.niw.backend.dto.PaymentDTO;
import com.niw.backend.dto.UserDTO;
import com.niw.backend.entity.PaymentEntity;
import com.niw.backend.entity.UserEntity;
import com.niw.backend.mapper.PaymentMapper;
import com.niw.backend.mapper.UserMapper;
import com.niw.backend.payload.response.UserResponse;
import com.niw.backend.repository.PaymentRepository;
import com.niw.backend.repository.UserRepository;
import com.niw.backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 8:14 PM
 **/

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FactorProperties factorProperties;

    public PaymentServiceImpl
            (
                    PaymentRepository paymentRepository,
                    PaymentMapper paymentMapper,
                    UserRepository userRepository,
                    UserMapper userMapper,
                    FactorProperties factorProperties
            ) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.factorProperties = factorProperties;
    }

    @Override
    public UserResponse calculatePayment(@Valid UserDTO userDto) {
        try {
            float financingFactor = 0;
            float monthlyPayment = 0;

            if (userDto.paymentDTO().financingFactor().equals("INTERNAL")) {
                financingFactor = factorProperties.getInternal();
                // calculate monthly payment
                monthlyPayment = calculate(
                        userDto.paymentDTO().vehiclePrice(),
                        financingFactor,
                        userDto.paymentDTO().monthlyPayment());

            } else if (userDto.paymentDTO().financingFactor().equals("EXTERNAL")) {
                financingFactor = factorProperties.getExternal();
                // calculate monthly payment
                monthlyPayment = calculate(
                        userDto.paymentDTO().vehiclePrice(),
                        financingFactor,
                        userDto.paymentDTO().monthlyPayment());
            }

            // convert payment dto to entity and save
            PaymentEntity paymentEntity = paymentMapper.toEntity(userDto.paymentDTO());
            paymentEntity.setToPayEveryMonth(monthlyPayment);
            paymentEntity.setFinancingFactor(String.valueOf(financingFactor));
            paymentRepository.save(paymentEntity);

            // convert user dto to entity and save
            UserEntity userEntity = userMapper.toEntity(userDto);
            userEntity.setPaymentId(paymentEntity.getId());
            userRepository.save(userEntity);

            // reconvert payment entity to dto for response
            PaymentDTO paymentDTO = paymentMapper.toDto(paymentEntity);

            return new UserResponse(
                    userDto.username(),
                    userDto.email(),
                    paymentDTO
            );
        } catch (Exception e) {
            log.error("Error on calculatePayment: {}", e.getMessage());
            return null;
        }
    }

    private float calculate(float vehiclePrice, float financingFactor, float monthlyPayment) {
        float calculateFactor = financingFactor / 100;
        float result = vehiclePrice * calculateFactor;
        result = (result + vehiclePrice) / monthlyPayment;
        return result;
    }

    private void exportData() throws IOException {
        // export data to csv
        FileWriter fileWriter = new FileWriter("data.csv");
        try {
            fileWriter.append("username,email,vehiclePrice,financingFactor,monthlyPayment,toPayEveryMonth\n");
            userRepository.findAll().forEach(userEntity -> {
                PaymentEntity paymentEntity = paymentRepository.findById(userEntity.getPaymentId()).get();
                try {
                    fileWriter.append(userEntity.getUsername())
                            .append(",")
                            .append(userEntity.getEmail())
                            .append(",")
                            .append(String.valueOf(paymentEntity.getVehiclePrice()))
                            .append(",")
                            .append(paymentEntity.getFinancingFactor())
                            .append(",")
                            .append(String.valueOf(paymentEntity.getMonthlyPayment()))
                            .append(",")
                            .append(String.valueOf(paymentEntity.getToPayEveryMonth()))
                            .append("\n");
                } catch (IOException e) {
                    log.error("Error on exportData: {}", e.getMessage());
                }
            });
        } catch (Exception e) {
            log.error("Error on exportData: {}", e.getMessage());
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }
}
