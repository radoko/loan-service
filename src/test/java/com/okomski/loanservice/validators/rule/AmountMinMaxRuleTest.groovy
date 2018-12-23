package com.okomski.loanservice.validators.rule

import com.okomski.loanservice.config.CommonBusinessRuleProperties
import com.okomski.loanservice.rest.dto.LoanApplicationRequest
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class AmountMinMaxRuleTest extends Specification {

    @Unroll
    def "should check isValid"() {
        given:
        def properties = Mock(CommonBusinessRuleProperties)
        properties.getMaxAmount() >> 10000;
        properties.getMinAmount() >> 100;

        def testLoanRequest = new LoanApplicationRequest(
                amount: _amount,
                term: LocalDateTime.now()
        )

        def rule = new AmountMinMaxRule()
        rule.commonBusinessRuleProperties = properties

        expect:
        rule.isValid(testLoanRequest) == _valid

        where:
        _amount                   | _valid
        BigDecimal.valueOf(100)   | true
        BigDecimal.valueOf(99)    | false
        BigDecimal.valueOf(101)   | true
        BigDecimal.valueOf(10000) | true
        BigDecimal.valueOf(10001) | false
    }
}
