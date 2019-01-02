package com.okomski.loanservice.validators.rule

import com.okomski.loanservice.config.CommonBusinessRuleProperties
import com.okomski.loanservice.rest.dto.LoanApplicationRequest
import com.okomski.loanservice.utils.SystemClock
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalDateTime

class TermMinMaxRuleTest extends Specification {

    @Unroll
    def "should check isValid"() {
        given:
        def mockClock = Mock(SystemClock)
        def now = LocalDate.of(2019, 1, 2)
        mockClock.localDateTimeNow() >> now.atStartOfDay()
        mockClock.localDateNow() >> now

        def properties = Mock(CommonBusinessRuleProperties)
        def validTermDays = 2
        properties.getMaxValidTermInDays() >> validTermDays;

        def testLoanRequest = new LoanApplicationRequest(
                amount: 1000,
                term: _term
        )

        def rule = new TermMinMaxRule()
        rule.systemClock = mockClock
        rule.commonBusinessRuleProperties = properties

        expect:
        rule.isValid(testLoanRequest) == _valid

        where:
        _term                              | _valid
        //Before valid term
        LocalDateTime.of(2019, 1, 1, 0, 0) | false
        //Between valid term
        LocalDateTime.of(2019, 1, 3, 0, 0) | true
        //After valid term
        LocalDateTime.of(2019, 1, 5, 0, 0) | false
    }
}
