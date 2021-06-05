package org.propagate.server.config;

import org.propagate.common.dao.FeatureFlagDao;
import org.propagate.common.dao.UserDao;
import org.propagate.core.auth.AuthenticatorFactory;
import org.propagate.core.auth.EmailPasswordAuthenticatorImpl;
import org.propagate.core.service.FeatureFlagService;
import org.propagate.core.service.UserService;
import org.propagate.core.service.impl.FeatureFlagServiceImpl;
import org.propagate.core.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}