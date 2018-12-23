package com.okomski.loanservice.rest;

import com.okomski.loanservice.rest.dto.ErrorResponse;
import com.okomski.loanservice.rest.dto.LoanApplicationRequest;
import com.okomski.loanservice.rest.dto.LoanApplicationResponse;
import com.okomski.loanservice.rest.dto.RestResponse;
import com.okomski.loanservice.service.LoanService;
import com.okomski.loanservice.validators.exceptions.ApplicationRejected;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping(path = "/apply", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoanApplicationResponse> applyForLoan(@RequestBody LoanApplicationRequest loanApplicationRequest) {
        return ResponseEntity.ok().body(loanService.applyForLoan(loanApplicationRequest));
    }

    @PostMapping(path = "/{loanId}/extend")
    public ResponseEntity<RestResponse> extendLoan(@PathVariable("loanId") Long loanId) {
        loanService.extendLoan(loanId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ApplicationRejected.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleApplicationRejected(HttpServletRequest request, ApplicationRejected applicationRejected) {
        return new ErrorResponse(applicationRejected.getErrors(), request.getServletPath());
    }

}
