package com.abhishek.lendingengine.domain.repository;

import com.abhishek.lendingengine.domain.model.Loan;
import com.abhishek.lendingengine.domain.model.Status;
import com.abhishek.lendingengine.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByBorrowerAndStatus(User borrower, Status status);
    List<Loan> findAllByLenderAndStatus(User lender, Status status);
    Optional<Loan> findOneByIdAndBorrower(long loanId, User borrower);

}
