package org.propagate.client.provider.websocket.spi;

public interface IWebSocket extends Runnable, AutoCloseable {
    interface Listener {
        void onText(IWebSocket IWebSocket, CharSequence data);

        void onError(IWebSocket IWebSocket, Throwable error);
    }
}
