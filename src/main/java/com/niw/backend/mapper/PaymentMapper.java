package com.niw.backend.mapper;

import com.niw.backend.dto.PaymentDTO;
import com.niw.backend.entity.PaymentEntity;
import com.niw.backend.payload.request.PaymentRequest;
import com.niw.backend.payload.response.PaymentResponse;
import org.mapstruct.*;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:34 PM
 **/

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    PaymentEntity toEntity(PaymentDTO paymentDto);
    PaymentDTO toDto(PaymentEntity paymentEntity);
    PaymentResponse toResponse(PaymentDTO paymentDTO);
    PaymentDTO fromRequest(PaymentRequest paymentRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PaymentEntity partialUpdate(PaymentDTO paymentDTO, @MappingTarget PaymentEntity payment);
}
