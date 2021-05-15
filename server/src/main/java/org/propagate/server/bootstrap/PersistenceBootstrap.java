package org.propagate.server.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class PersistenceBootstrap {

//    @PostConstruct
//    private void init() {
//        DatabaseMigrationHelper migrationHelper = new DatabaseMigrationHelper("jdbc:postgresql://localhost:5432/propagate", "root", "");
//        migrationHelper.migrate();
//        log.info("All migrations executed...");
//    }

//    @Bean
//    public HikariConfig buildHikariConfig() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/propagate");
//        hikariConfig.setUsername("root");
//        return hikariConfig;
//    }
//
//    @Bean
//    public DataSource createConnectionPool(final HikariConfig cfg) {
//        return new HikariDataSource(cfg);
//    }
//
//    @Bean
//    public FeatureFlagDao buildFeatureFlagDao(final SpringDataJpaFeatureFlagDao dao) {
//        return new PostgresFeatureFlagDaoImpl(dao);
//    }
}
