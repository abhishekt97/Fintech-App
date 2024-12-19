package com.abhishek.lendingengine.domain.model;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Balance {


    @Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Currency, Money> getMoneyMap() {
        return moneyMap;
    }

    @ElementCollection  // map collection to different table
    @MapKeyClass(Currency.class)    //map key class from other table (in this case currency)
    @OneToMany(targetEntity = Money.class, cascade = CascadeType.ALL)
    private Map<Currency, Money> moneyMap = new HashMap<>();

    public void topUp(final Money money){
        if (moneyMap.get(money.getCurrency()) == null) {
            moneyMap.put(money.getCurrency(), money);
        }else {
            moneyMap.put(money.getCurrency(), moneyMap.get(money.getCurrency()).addMoney(money));
        }
    }

    public void withdraw(final Money money){
        final Money moneyInBalance = moneyMap.get(money.getCurrency());
        if (moneyInBalance == null) {
            throw new IllegalStateException();
        }else {
            moneyMap.put(money.getCurrency(), moneyMap.get(money.getCurrency()).subtractMoney(money));
        }
    }
}
