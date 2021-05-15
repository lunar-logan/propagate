package org.propagate.client;

import org.propagate.client.spi.PropagateClient;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SseConsumer {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        // start the propagate client
        final PropagateClient client = PropagateClient.builder()
                .environment("production")
                .baseUrl("http://localhost:8080")
                .buildPollingClient();
        client.run();

        String key = "sd-partial-delivery";
//        Thread.sleep(12000);
        for (int i = 0; i < 100; i++) {
            String result = client.eval(key, Map.of("a", "6", "b", "66"), () -> "false");
            System.out.println(i + ". Result >>> " + result);
            Thread.sleep(3000);
        }


//        client.close();
        latch.await();
        client.close();
    }
}
