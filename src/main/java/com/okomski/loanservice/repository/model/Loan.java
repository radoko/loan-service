package com.okomski.loanservice.repository.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private BigDecimal loanAmount;

    @Column
    private String loanCurrency;

    @Column
    private BigDecimal loanInterest;

    @Column
    private LocalDateTime originDueDate;

    @Column
    private LocalDateTime dueDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanEvent> eventHistory;

    public BigDecimal getTotalLoanAmount() {
        return loanAmount.add(loanInterest);
    }
}
