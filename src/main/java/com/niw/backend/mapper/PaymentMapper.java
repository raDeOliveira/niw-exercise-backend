package com.niw.backend.mapper;


import com.niw.backend.dto.PaymentDTO;
import com.niw.backend.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:34 PM
 **/

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    PaymentEntity toEntity(PaymentDTO paymentDto);
    PaymentDTO toDto(PaymentEntity paymentEntity);
}
