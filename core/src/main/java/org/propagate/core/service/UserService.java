package org.propagate.core.service;

import org.propagate.common.domain.User;
import org.propagate.common.rest.entity.AuthenticationRequest;

import java.util.Optional;

public interface UserService {
    User create(User user);

    Optional<User> getById(String id);

    Optional<User> authenticate(AuthenticationRequest request);

    void deleteById(String id);
}
