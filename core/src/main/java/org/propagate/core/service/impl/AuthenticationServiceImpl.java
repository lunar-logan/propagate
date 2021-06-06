package org.propagate.core.service.impl;

import org.propagate.common.rest.entity.AuthenticationRequest;
import org.propagate.common.rest.entity.AuthenticationResponse;
import org.propagate.core.service.AuthenticationService;
import org.propagate.core.service.TokenService;
import org.propagate.core.service.UserService;


public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationServiceImpl(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return userService.authenticate(request)
                .map(tokenService::issueToken)
                .map(token -> AuthenticationResponse.builder().token(token).build())
                .orElseGet(() -> AuthenticationResponse.builder().success(false).build());
    }
}
