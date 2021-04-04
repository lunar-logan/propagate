package org.propagate.client;

import org.propagate.client.spi.PropagateClient;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SseConsumer {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        // start the propagate client
        PropagateClient client = PropagateClient.newPropagateClient("production", "ws://localhost:8080/change-events");
        client.run();

        String key = "shipping.partial.delivery.rollout";
        Thread.sleep(12000);
        for (int i = 0; i < 100; i++) {
            String result = client.eval(key, Map.of("a", "6", "b", "66"), () -> "false");
            System.out.println(i + ". Result >>> " + result);
            Thread.sleep(3000);
        }


//        client.close();
        latch.await();
    }
}
