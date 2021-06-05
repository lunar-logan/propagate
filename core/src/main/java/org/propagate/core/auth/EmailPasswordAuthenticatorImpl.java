package org.propagate.core.auth;

import org.propagate.common.dao.UserDao;
import org.propagate.common.domain.User;

import java.util.Optional;

public class EmailPasswordAuthenticatorImpl implements Authenticator {
    private final UserDao userDao;

    public EmailPasswordAuthenticatorImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> authenticate(String principal, String credential) {
        return userDao.findByEmail(principal)
                .filter(user -> PasswordUtils.check(credential, user.getPassword()));
    }
}
