package org.propagate.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class PropagateServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PropagateServer.class).build().run(args);
    }
}
