package org.propagate.core.auth;

import org.propagate.common.domain.User;

import java.util.Optional;

public interface Authenticator {
    Optional<User> authenticate(String principal, String credential);
}
