package org.propagate.core.service.impl;

import org.propagate.common.dao.UserDao;
import org.propagate.common.domain.User;
import org.propagate.common.rest.entity.AuthenticationRequest;
import org.propagate.core.auth.Authenticator;
import org.propagate.core.auth.AuthenticatorFactory;
import org.propagate.core.service.UserService;

import java.util.Optional;


public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final AuthenticatorFactory authenticatorFactory;

    public UserServiceImpl(UserDao userDao, AuthenticatorFactory authenticatorFactory) {
        this.userDao = userDao;
        this.authenticatorFactory = authenticatorFactory;
    }

    @Override
    public User create(User user) {
        return userDao.save(user);
    }

    @Override
    public Optional<User> getById(String id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> authenticate(AuthenticationRequest request) {
        final Authenticator authenticator = authenticatorFactory.getInstance(request.getAuthType().name());
        return authenticator.authenticate(request.getPrincipal(), request.getCredential());
    }

    @Override
    public void deleteById(String id) {
        userDao.deleteById(id);
    }
}
