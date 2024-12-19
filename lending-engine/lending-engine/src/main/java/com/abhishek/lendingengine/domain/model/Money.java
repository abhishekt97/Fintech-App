package com.abhishek.lendingengine.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
public class Money {

    public static final Money ZERO_MONEY = new Money(Currency.INR, 0);
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Id
    @GeneratedValue
    private long id;
    private  Currency currency;
    private BigDecimal amount;

    public Money timesInterest(double multiplier){
        return new Money(Currency.INR, amount.doubleValue() * multiplier);
    }

    public Money() {
    }

    public Money(long id, Currency currency, double amount) {
        this.id = id;
        this.currency = currency;
        this.amount = BigDecimal.valueOf(amount);
    }
    public Money(Currency currency, double amount) {
        this.currency = currency;
        this.amount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_DOWN);
    }
    public Money addMoney(final Money money){
        if(currency != money.getCurrency()){
            throw new IllegalArgumentException();
        }
        return new Money( currency, amount.doubleValue()+ money.getAmount());
    }

    public Money subtractMoney(final Money money){
        if(currency != money.getCurrency() || amount.doubleValue() < money.getAmount()){
            throw new IllegalArgumentException();
        }
        return new Money( currency, amount.doubleValue()- money.getAmount());
    }

    public Currency getCurrency() {
        return currency;
    }


    public double getAmount() {
        return amount.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return currency == money.currency && Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return "Money{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }
}
