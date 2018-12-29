package com.okomski.loanservice;

import static com.okomski.loanservice.service.LoanServiceImpl.INTEREST_PERCENTAGE_CONFIG;
import static org.springframework.test.util.AssertionErrors.assertEquals;

import com.okomski.loanservice.repository.LoanRepository;
import com.okomski.loanservice.repository.model.Loan;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.rest.dto.LoanApplicationResponse;
import com.okomski.loanservice.utils.BigDecimalUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanServiceApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void applyForLoan() {
        BigDecimal amount = new BigDecimal(7000);
        LocalDateTime term = LocalDateTime.now();
        LoanApplicationRequest loanApplicationRequest = LoanApplicationRequest.builder()
                .amount(amount).term(term).build();
        BigDecimal expectedTotalAmount = amount.add(BigDecimalUtils.percentage(amount, INTEREST_PERCENTAGE_CONFIG));

        ResponseEntity<LoanApplicationResponse> responseEntity =
                restTemplate.postForEntity("/loan/apply", loanApplicationRequest, LoanApplicationResponse.class);
        LoanApplicationResponse loanApplicationResponse = responseEntity.getBody();

        assertEquals("Assertion error - status code", HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Assertion error - total loan amount", expectedTotalAmount, loanApplicationResponse.getTotalLoanAmount());
        assertEquals("Assertion error - term", term, loanApplicationResponse.getDueDate());
    }

    @Test
    public void extendLoan() {
        LocalDateTime now = LocalDateTime.now();
        Loan dummyLoan = loanRepository.save(Loan.builder()
                .dueDate(now).loanAmount(new BigDecimal(7000)).build());

        ResponseEntity<Void> response = restTemplate.exchange("/loan/" + dummyLoan.getId() + "/extend", HttpMethod.POST, null, Void.class);

        assertEquals("Assertion error - status code", HttpStatus.OK, response.getStatusCode());
    }

}
