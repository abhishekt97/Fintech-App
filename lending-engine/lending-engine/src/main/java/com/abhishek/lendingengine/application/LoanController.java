package com.abhishek.lendingengine.application;

import com.abhishek.lendingengine.domain.repository.LoanApplicationRepository;
import com.abhishek.lendingengine.application.model.LoanRepaymentRequest;
import com.abhishek.lendingengine.application.model.LoanRequest;
import com.abhishek.lendingengine.application.service.TokenValidationService;
import com.abhishek.lendingengine.domain.model.Loan;
import com.abhishek.lendingengine.domain.model.LoanApplication;
import com.abhishek.lendingengine.domain.model.Status;
import com.abhishek.lendingengine.domain.model.User;
import com.abhishek.lendingengine.domain.service.LoanApplicationAdapter;
import com.abhishek.lendingengine.domain.service.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;

    @Autowired  //constructor based injection
    public LoanController(LoanApplicationRepository loanApplicationRepository, LoanApplicationAdapter loanApplicationAdapter, LoanService loanService, TokenValidationService tokenValidationService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
        this.tokenValidationService = tokenValidationService;
    }

    @GetMapping("/loan/borrowed/{status}")
    public List<Loan> findBorrowedLoans(@RequestHeader String authorization,
                                        @PathVariable Status status){
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllBorrowedLoans(borrower, status);
    }

    @GetMapping("/loan/lent/{status}")
    public List<Loan> findLentLoans(@RequestHeader String authorization,
                                    @PathVariable Status status){
        User lender = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllLentLoans(lender, status);
    }


    @PostMapping(value = "/loan/request")
    public void loadRequest(@RequestBody LoanRequest loanRequest, HttpServletRequest request){
        User borrower = tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        loanApplicationRepository.save(loanApplicationAdapter.transform(loanRequest, borrower));
    }

    @GetMapping(value = "/loan/requests")
    public List<LoanApplication> findLoanApplications(HttpServletRequest request){
        tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return loanApplicationRepository.findAllByStatusEquals(Status.NOT_PAID);
    }

    @PostMapping(value = "/loan/accept/{loanApplicationId}")
    public void acceptLoan(@PathVariable final String loanApplicationId,
                           HttpServletRequest request){
        User lender = tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        loanService.acceptLoan(loanApplicationId, lender.getUsername());
    }

    @GetMapping(value = "/loans")
    public List<Loan> getLoan(){
        return loanService.getLoan();
    }

    @PostMapping(value = "/loan/repay")
    public void repayLoan(@RequestBody LoanRepaymentRequest repaymentRequest,
                          @RequestHeader String authorization){
        User borrower = tokenValidationService.validateTokenAndGetUser(authorization);
        loanService.repayLoan(repaymentRequest.getAmount(), repaymentRequest.getLoanId(), borrower);
    }
}
