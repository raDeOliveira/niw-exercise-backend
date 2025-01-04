package com.niw.backend.Properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @project: niw-java-exercise
 * @author: raOliveira
 * @time: 7:25 PM
 **/

@Data
@Component
@PropertySource("classpath:factor.properties")
public class FactorProperties {

    @Value("${factor.internal}")
    private int internal;
    @Value("${factor.external}")
    private float external;

}
