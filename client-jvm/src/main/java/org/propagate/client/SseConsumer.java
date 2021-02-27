package org.propagate.client;

import org.propagate.client.spi.PropagateClient;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SseConsumer {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
//        PropagateClient client = PropagateClient.newBuilder("production")
//                .withConnectionUri("http://localhost:8080/api/v1/ff")
//                .build();
//
//        client.run();
//
//        Thread.sleep(7000);
//        String result = client.eval("SNAPDEALTECH-90526-rollout-partial-delivery", Map.of("a", "6", "b", "66"), () -> "false");
//        System.out.println("Result >>> " + result);

        PropagateClient client = PropagateClient.newPropagateClient("production", "ws://localhost:8080/update-events");
        client.run();

        Thread.sleep(12000);
        String result = client.eval("SNAPDEALTECH-90526-rollout-partial-delivery", Map.of("a", "6", "b", "66"), () -> "false");
        System.out.println("Result >>> " + result);

        latch.await();
    }
}
