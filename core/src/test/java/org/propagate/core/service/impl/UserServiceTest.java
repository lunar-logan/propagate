package org.propagate.core.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.propagate.common.dao.UserDao;
import org.propagate.common.domain.User;
import org.propagate.common.rest.entity.AuthenticationRequest;
import org.propagate.core.auth.Authenticator;
import org.propagate.core.auth.AuthenticatorFactory;
import org.propagate.core.auth.EmailPasswordAuthenticatorImpl;
import org.propagate.core.auth.PasswordUtils;
import org.propagate.core.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private UserService userService;
    private final User user = User.builder()
            .id("1")
            .email("johndoe@example.com")
            .password(PasswordUtils.hash("123", PasswordUtils.genSalt()))
            .firstName("John")
            .build();

    @BeforeEach
    public void setup() {
        UserDao dao = mock(UserDao.class);
        when(dao.findById(anyString())).thenReturn(Optional.of(user));
        when(dao.findByEmail(anyString())).thenReturn(Optional.of(user));

        Authenticator emailPasswordAuthenticator = new EmailPasswordAuthenticatorImpl(dao);
        AuthenticatorFactory factory = mock(AuthenticatorFactory.class);
        when(factory.getInstance(anyString())).thenReturn(emailPasswordAuthenticator);

        userService = new UserServiceImpl(dao, factory);
    }

    @Test
    public void authenticate__correctly_authenticates_user() {
        final AuthenticationRequest request = AuthenticationRequest.builder()
                .principal("johndoe@example.com")
                .credential("123")
                .build();
        Optional<User> optionalUser = userService.authenticate(request);
        assertTrue(optionalUser.isPresent());
        assertEquals(user, optionalUser.get());
        assertEquals("1", optionalUser.get().getId());
    }

}