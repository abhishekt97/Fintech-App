package com.abhishek.lendingengine.application.service;

import com.abhishek.lendingengine.domain.model.User;

public interface TokenValidationService {

    User validateTokenAndGetUser(final String token);
}
