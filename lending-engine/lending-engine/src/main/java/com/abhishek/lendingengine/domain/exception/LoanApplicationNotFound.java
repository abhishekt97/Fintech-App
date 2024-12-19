package com.abhishek.lendingengine.domain.exception;

public class LoanApplicationNotFound extends RuntimeException{

    public LoanApplicationNotFound(String message) {
        super(message);
    }
}
