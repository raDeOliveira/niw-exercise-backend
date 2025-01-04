package com.niw.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 6:14 PM
 **/

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_entity")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "to_pay_every_month")
    private float toPayEveryMonth;
    @Column(name = "vehicle_price")
    private float vehiclePrice;
    @Column(name = "financing_factor")
    private String financingFactor;
    @Column(name = "monthly_payment")
    private int monthlyPayment;

}
