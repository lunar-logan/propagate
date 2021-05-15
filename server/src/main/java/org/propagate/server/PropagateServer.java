package org.propagate.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "org.propagate")
@EnableMongoRepositories(basePackages = "org.propagate.persistence")
@EntityScan(basePackages = "org.propagate.persistence")
public class PropagateServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PropagateServer.class).build().run(args);
    }
}
