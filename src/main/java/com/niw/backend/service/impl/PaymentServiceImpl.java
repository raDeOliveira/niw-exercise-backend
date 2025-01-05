package com.niw.backend.service.impl;

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

    public PaymentResponse calculatePayment(@Valid PaymentDTO paymentDTO) {
            float financingFactor = 0;
            float toPayEveryMonth = 0;
        try {
            if (paymentDTO.financingFactor().equals("INTERNAL")) {
                financingFactor = factorProperties.getInternal();
                // calculate monthly payment
                toPayEveryMonth = calculate(
                        paymentDTO.vehiclePrice(),
                        financingFactor,
                        paymentDTO.monthlyPayment());

            } else if (paymentDTO.financingFactor().equals("EXTERNAL")) {
                financingFactor = factorProperties.getExternal();
                // calculate monthly payment
                toPayEveryMonth = calculate(
                        paymentDTO.vehiclePrice(),
                        financingFactor,
                        paymentDTO.monthlyPayment());
            }

            // convert payment dto to entity and save
            PaymentEntity paymentEntity = paymentMapper.toEntity(paymentDTO);

            // reconvert payment entity to dto for response
            PaymentDTO dto = paymentMapper.toDto(paymentEntity);

            return new PaymentResponse(
                    toPayEveryMonth,
                    financingFactor,
                    dto.monthlyPayment(),
                    dto.vehiclePrice()
            );
        } catch (Exception e) {
            log.error("Error on savePayment: {}", e.getMessage());
            return null;
        }
    }

    // calculate formula
    private float calculate(float vehiclePrice, float financingFactor, float monthlyPayment) {
        float calculateFactor = financingFactor / 100;
        float result = vehiclePrice * calculateFactor;
        result = (result + vehiclePrice) / monthlyPayment;
        return result;
    }

    public UserResponse saveAndExportData(UserDTO userDto) throws IOException {

        // save data to database
        PaymentEntity paymentEntity = paymentMapper.toEntity(userDto.paymentDTO());
        paymentEntity.setToPayEveryMonth(paymentEntity.getToPayEveryMonth());
        paymentEntity.setFinancingFactor(String.valueOf(paymentEntity.getFinancingFactor()));
        paymentRepository.save(paymentEntity);

        UserEntity userEntity = userMapper.toEntity(userDto);
        userEntity.setPaymentId(paymentEntity.getId());
        userRepository.save(userEntity);

        // export data to csv
        FileWriter fileWriter = new FileWriter("data.csv");
        try {
            fileWriter.append("name,email,vehiclePrice,financingFactor,monthlyPayment,toPayEveryMonth\n");
            fileWriter.append(userDto.name())
                    .append(",")
                    .append(userDto.email())
                    .append(",")
                    .append(String.valueOf(userDto.paymentDTO().vehiclePrice()))
                    .append(",")
                    .append(userDto.paymentDTO().financingFactor())
                    .append(",")
                    .append(String.valueOf(userDto.paymentDTO().monthlyPayment()))
                    .append(",")
                    .append(String.valueOf(userDto.paymentDTO().toPayEveryMonth()))
                    .append("\n");

        } catch (Exception e) {
            log.error("Error on exportData: {}", e.getMessage());
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
        return new UserResponse(userDto.name(), userDto.email(), userDto.paymentDTO());
    }
}
