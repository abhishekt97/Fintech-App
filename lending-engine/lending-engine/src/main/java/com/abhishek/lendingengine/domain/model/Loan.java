package com.abhishek.lendingengine.domain.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Loan {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private User borrower;
    @ManyToOne
    private User lender;
    @OneToOne(cascade = CascadeType.ALL)
    private Money loanAmount;
    private double interestRate;
    private LocalDate dateLent;
    private LocalDate dateDue;
    @OneToOne(cascade = CascadeType.ALL)
    private Money amountPaid;
    private Status status;


    public Money getAmountOwned(){
        return loanAmount.timesInterest(1 + interestRate/100).subtractMoney(amountPaid);
    }
    public void repayMoney(Money money){
        borrower.withdraw(money);
        lender.topUp(money);
        amountPaid=amountPaid.addMoney(money);

        if(getAmountOwned().equals(Money.ZERO_MONEY)){
            status = Status.PAID;
        }
    }

    public Loan() {
    }

    public Loan(User lender, LoanApplication loanApplication){
        this.borrower=loanApplication.getBorrower();
        this.lender=lender;
        this.loanAmount =loanApplication.getAmount();
        this.interestRate=loanApplication.getInterestRate();
        this.dateLent= LocalDate.now();
        this.dateDue = LocalDate.now().plusDays(loanApplication.getRepaymentTime());
        this.amountPaid = Money.ZERO_MONEY;
        this.status = Status.NOT_PAID;
    }
    public long getId() {
        return id;
    }

    public User getBorrower() {
        return borrower;
    }

    public User getLender() {
        return lender;
    }

    public LocalDate getDateLent() {
        return dateLent;
    }

    public Money getAmountPaid() {
        return amountPaid;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", borrower=" + borrower +
                ", lender=" + lender +
                ", loanAmount=" + loanAmount +
                ", interestRate=" + interestRate +
                ", dateLent=" + dateLent +
                ", dateDue=" + dateDue +
                ", amountPaid=" + amountPaid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return id == loan.id && Double.compare(loan.interestRate, interestRate) == 0 && Objects.equals(borrower, loan.borrower) && Objects.equals(lender, loan.lender) && Objects.equals(loanAmount, loan.loanAmount) && Objects.equals(dateLent, loan.dateLent) && Objects.equals(dateDue, loan.dateDue) && Objects.equals(amountPaid, loan.amountPaid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, borrower, lender, loanAmount, interestRate, dateLent, dateDue, amountPaid);
    }
}
