package com.okomski.loanservice.rest;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.rest.dto.LoanApplicationResponse;
import com.okomski.loanservice.service.LoanService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class LoanControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private LoanService loanService;

    @Test
    public void givenLoanApplication_whenApply_thenReturnApplicationId() throws Exception {
        LoanApplicationRequest loanApplicationRequest = LoanApplicationRequest.builder()
                .amount(new BigDecimal(7000)).term(LocalDateTime.now()).build();
        LoanApplicationResponse loanApplicationResponse = LoanApplicationResponse.builder().loanApplicationId(new Long(1)).build();

        given(loanService.applyForLoan(loanApplicationRequest)).willReturn(loanApplicationResponse);

        mockMvc.perform(post("/loan/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loanApplicationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("loanApplicationId", is(1)));

    }

    @Test
    public void givenLoanApplicationId_whenExtend_thenStatusOk() throws Exception {
        long loanId = 1;
        doNothing().when(loanService).extendLoan(loanId);

        mockMvc.perform(post("/loan/" + loanId + "/extend")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
