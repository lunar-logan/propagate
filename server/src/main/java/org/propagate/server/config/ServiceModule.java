package org.propagate.server.config;

import org.propagate.common.dao.FeatureFlagDao;
import org.propagate.core.service.FeatureFlagService;
import org.propagate.core.service.impl.FeatureFlagServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceModule {
    @Bean
    public FeatureFlagService provideFeatureFlagService(FeatureFlagDao featureFlagDao) {
        return new FeatureFlagServiceImpl(featureFlagDao);
    }
}