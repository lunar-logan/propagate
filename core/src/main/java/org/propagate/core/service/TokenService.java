package org.propagate.core.service;

import org.propagate.common.domain.User;

import java.util.Optional;

public interface TokenService {
    String issueToken(User user);
    Optional<User> verify(String token);
}
