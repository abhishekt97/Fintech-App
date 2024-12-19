package com.abhishek.lendingengine.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;

@Entity
public final class LoanApplication {

    @Id
    @UuidGenerator
    private String  id;
    private int amount;
    @ManyToOne
    private User borrower;
    private int repaymentTime;
    private double interestRate;

    private Status status;


    public LoanApplication(){}
    public LoanApplication(String id, int amount, User borrower, int repaymentTime, double interestRate) {
        this.amount = amount;
        this.id=id;
        this.borrower = borrower;
        this.repaymentTime = repaymentTime;
        this.interestRate = interestRate;
        this.status = Status.NOT_PAID;
    }

    public Loan acceptLoanApplication(final User lender){
        lender.withdraw(getAmount());
        borrower.topUp(getAmount());
        status=Status.PAID;
        return new Loan(lender, this);
    }

    public String  getId() {
        return id;
    }

    public Money getAmount() {
        return new Money(Currency.INR, amount);
    }

    public User getBorrower() {
        return borrower;
    }

    public int getRepaymentTime() {
        return repaymentTime;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanApplication that = (LoanApplication) o;
        return id == that.id && amount == that.amount && Double.compare(that.interestRate, interestRate) == 0 && Objects.equals(borrower, that.borrower) && Objects.equals(repaymentTime, that.repaymentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, borrower, repaymentTime, interestRate);
    }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "id=" + id +
                ", amount=" + amount +
                ", borrower=" + borrower +
                ", repaymentTime=" + repaymentTime +
                ", interestRate=" + interestRate +
                '}';
    }
}
