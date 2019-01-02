package com.okomski.loanservice.validators.rule

import com.okomski.loanservice.config.CommonBusinessRuleProperties
import com.okomski.loanservice.rest.dto.LoanApplicationRequest
import com.okomski.loanservice.utils.SystemClock
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime
import java.time.LocalTime

class MaxAmountAllowedRuleTest extends Specification {

    @Unroll
    def "should check isValid"() {
        given:
        def mockClock = Mock(SystemClock)
        mockClock.localTime() >> _nowTime

        def properties = Mock(CommonBusinessRuleProperties)
        properties.getMaxAmount() >> 10000;

        def testLoanRequest = new LoanApplicationRequest(
                amount: _amount,
                term: LocalDateTime.of(2019, 1, 1, 0, 0)
        )

        def rule = new MaxAmountAllowedRule()
        rule.systemClock = mockClock
        rule.commonBusinessRuleProperties = properties

        expect:
        rule.isValid(testLoanRequest) == _valid

        where:
        _nowTime           | _amount               | _valid
        //Max amount asked on beginning valid time
        LocalTime.of(0, 0) | new BigDecimal(10000) | true
        //Max amount asked between valid time
        LocalTime.of(3, 0) | new BigDecimal(10000) | true
        //Max amount asked on ending valid time
        LocalTime.of(6, 0) | new BigDecimal(10000) | true
        //Max amount asked after valid time
        LocalTime.of(6, 1) | new BigDecimal(10000) | false
        //Other amount asked after valid time
        LocalTime.of(6, 1) | new BigDecimal(9999)  | true
    }
}
