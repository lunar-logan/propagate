package org.propagate.server.config;

import net.aholbrook.paseto.meta.PasetoBuilders;
import net.aholbrook.paseto.service.Token;
import org.propagate.common.dao.FeatureFlagDao;
import org.propagate.common.dao.UserDao;
import org.propagate.core.auth.AuthenticatorFactory;
import org.propagate.core.auth.EmailPasswordAuthenticatorImpl;
import org.propagate.core.service.AuthenticationService;
import org.propagate.core.service.FeatureFlagService;
import org.propagate.core.service.TokenService;
import org.propagate.core.service.UserService;
import org.propagate.core.service.impl.AuthenticationServiceImpl;
import org.propagate.core.service.impl.FeatureFlagServiceImpl;
import org.propagate.core.service.impl.TokenServiceImpl;
import org.propagate.core.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
public class ServiceModule {

    @Bean
    public FeatureFlagService provideFeatureFlagService(FeatureFlagDao featureFlagDao) {
        return new FeatureFlagServiceImpl(featureFlagDao);
    }

    @Bean
    public AuthenticatorFactory provideAuthenticatorFactory(UserDao userDao) {
        return new AuthenticatorFactory(new EmailPasswordAuthenticatorImpl(userDao));
    }

    @Bean
    public UserService provideUserService(UserDao userDao, AuthenticatorFactory factory) {
        return new UserServiceImpl(userDao, factory);
    }

    @Bean
    public net.aholbrook.paseto.service.TokenService<Token> providePasetoTokenService() {
        byte[] key = "707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f".getBytes(StandardCharsets.UTF_8);
        return PasetoBuilders.V2.localService(() -> key, Token.class)
                .withDefaultValidityPeriod(Duration.ofDays(15).getSeconds())
                .build();
    }

    @Bean
    public TokenService provideTokenService(UserService userService, net.aholbrook.paseto.service.TokenService<Token> pasetoService) {
        return new TokenServiceImpl(userService, pasetoService);
    }

    @Bean
    public AuthenticationService provideAuthenticationService(TokenService tokenService, UserService userService) {
        return new AuthenticationServiceImpl(userService, tokenService);
    }
}