package org.propagate.reactive;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class PropagateReactiveServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(PropagateReactiveServer.class).build().run(args);
    }
}
