package org.propagate.client;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SseConsumer {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        HighLevelPropagateClient client = HighLevelPropagateClient.newBuilder("\"production\"")
                .withConnectionUri("http://localhost:8080/api/v1/ff")
                .build();

        client.start();

Thread.sleep(7000);
        String result = client.eval("SNAPDEALTECH-90526-rollout-partial-delivery", Map.of("a", "44"), () -> "false");
        System.out.println("Result >>> " + result);

        latch.await();
    }
}
