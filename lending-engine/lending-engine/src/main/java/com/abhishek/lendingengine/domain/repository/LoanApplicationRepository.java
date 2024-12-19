package com.abhishek.lendingengine.domain.repository;

import com.abhishek.lendingengine.domain.model.LoanApplication;
import com.abhishek.lendingengine.domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, String> {

    List<LoanApplication> findAllByStatusEquals(Status status);

}
