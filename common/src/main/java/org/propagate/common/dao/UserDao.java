package org.propagate.common.dao;

import org.propagate.common.domain.User;

import java.util.Optional;

public interface UserDao extends CrudOperations<User, String> {
    Optional<User> findByEmail(String email);
}
