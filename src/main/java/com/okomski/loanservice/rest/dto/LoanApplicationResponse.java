package com.okomski.loanservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationResponse implements RestResponse{
    private Long loanApplicationId;
    private LocalDateTime dueDate;
    private BigDecimal totalLoanAmount;
}
