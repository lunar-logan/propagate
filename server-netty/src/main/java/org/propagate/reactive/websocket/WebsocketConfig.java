package org.propagate.reactive.websocket;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class WebsocketConfig {
    private static final String NOTIFICATION_PATH = "/change-events";
    private final WebsocketChangeNotifier changeNotifier;

    @Bean
    public HandlerMapping provideWebSocketHandlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1);
        handlerMapping.setUrlMap(createWebSocketUrlMap());
        return handlerMapping;
    }

    private Map<String, WebSocketHandler> createWebSocketUrlMap() {
        Map<String, WebSocketHandler> wsHandlerMap = new HashMap<>();
        wsHandlerMap.put(NOTIFICATION_PATH, changeNotifier);
        return wsHandlerMap;
    }
}
