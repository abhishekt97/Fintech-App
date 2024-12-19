package com.abhishek.lendingengine.application.model;

import com.abhishek.lendingengine.domain.model.Currency;
import com.abhishek.lendingengine.domain.model.Money;

import java.util.Objects;

public class LoanRepaymentRequest {

    private final long loanId;
    private final double amount;

    public LoanRepaymentRequest(long loanId, double amount) {
        this.loanId = loanId;
        this.amount = amount;
    }

    public long getLoanId() {
        return loanId;
    }

    // enhancing it. by passing just amount converting it to money
    public Money getAmount() {
        return new Money(Currency.INR, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanRepaymentRequest that = (LoanRepaymentRequest) o;
        return loanId == that.loanId && Double.compare(that.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, amount);
    }

    @Override
    public String toString() {
        return "LoanRepaymentRequest{" +
                "loanId=" + loanId +
                ", amount=" + amount +
                '}';
    }
}
