package com.okomski.loanservice.config;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("rule")
@Data
public class CommonBusinessRuleProperties {

    @NotNull
    private Long extensionDays;

    @NotNull
    private BigDecimal minAmount;

    @NotNull
    private BigDecimal maxAmount;

    @NotNull
    private Integer maxValidTermInDays;
}
