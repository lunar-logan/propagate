package org.propagate.core.service.impl;

import net.aholbrook.paseto.exception.PasetoTokenException;
import net.aholbrook.paseto.service.Token;
import org.propagate.common.domain.User;
import org.propagate.core.service.TokenService;
import org.propagate.core.service.UserService;

import java.util.Date;
import java.util.Optional;


public class TokenServiceImpl implements TokenService {
    private final UserService userService;
    private final net.aholbrook.paseto.service.TokenService<Token> pasetoService;

    public TokenServiceImpl(UserService userService, net.aholbrook.paseto.service.TokenService<Token> pasetoService) {
        this.userService = userService;
        this.pasetoService = pasetoService;
    }

    @Override
    public String issueToken(User user) {
        Token token = new Token();
        token.setTokenId(user.getId());
        token.setSubject(user.getId());
        token.setIssuedAt(new Date().getTime());
        return pasetoService.encode(token);
    }

    @Override
    public Optional<User> verify(String token) {
        try {
            final Token tok = pasetoService.decode(token);
            return userService.getById(tok.getSubject());
        } catch (PasetoTokenException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
