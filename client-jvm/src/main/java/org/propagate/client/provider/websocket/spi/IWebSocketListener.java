package org.propagate.client.provider.websocket.spi;

public interface IWebSocketListener {
    void onText(IWebSocket IWebSocket, CharSequence data);

    void onError(IWebSocket IWebSocket, Throwable error);
}
