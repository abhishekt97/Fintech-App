package com.abhishek.lendingengine.domain.service;

import com.abhishek.lendingengine.domain.model.*;
import com.abhishek.lendingengine.domain.repository.LoanApplicationRepository;
import com.abhishek.lendingengine.domain.repository.LoanRepository;
import com.abhishek.lendingengine.domain.repository.UserRepository;
import com.abhishek.lendingengine.domain.exception.LoanApplicationNotFound;
import com.abhishek.lendingengine.domain.exception.LoanNotFoundException;
import com.abhishek.lendingengine.domain.exception.UserNotFoundException;
import com.app.lendingengine.domain.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(LoanApplicationRepository loanApplicationRepository, LoanRepository loanRepository, UserRepository userRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    // when there is an action(change state - accept loan, repay loan) - return type loan b/c methods that mutate state shouldn't return anything and vice versa
    @Transactional
    public void acceptLoan(final String loanApplicationId, final String lenderId){

        LoanApplication loanApplication = findLoanApplication(loanApplicationId);
        User lender = findLender(lenderId);

        loanRepository.save(loanApplication.acceptLoanApplication(lender));
    }

    private User findLender(String lenderId) {
        return userRepository.findById(lenderId)
                .orElseThrow(() -> new UserNotFoundException("User not found for id : " + lenderId));
    }

    private LoanApplication findLoanApplication(String loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new LoanApplicationNotFound("Loan application not found for id : " + loanApplicationId));
    }

    public List<Loan> getLoan() {
        return loanRepository.findAll();
    }

    public List<Loan> findAllBorrowedLoans(User borrower, Status status) {
        return loanRepository.findAllByBorrowerAndStatus(borrower, status);
    }

    public List<Loan> findAllLentLoans(User lender, Status status) {
        return loanRepository.findAllByLenderAndStatus(lender, status);
    }

    @Transactional
    public void repayLoan(Money amountToRepay, long loanId, User borrower) {

        Loan loan = loanRepository.findOneByIdAndBorrower(loanId, borrower)
                .orElseThrow(LoanNotFoundException::new);

        Money actualPaidAmount = amountToRepay.getAmount() > loan.getAmountOwned().getAmount() ? loan.getAmountOwned() : amountToRepay;

        loan.repayMoney(actualPaidAmount);

    }
}
