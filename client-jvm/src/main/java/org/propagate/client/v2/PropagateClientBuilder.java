package org.propagate.client.v2;

import java.net.URI;

public class PropagateClientBuilder {
    private final String env;
    private String uri;

    public PropagateClientBuilder(String env) {
        this.env = env;
    }

    public PropagateClientBuilder withConnectionUri(String uri) {
        this.uri = uri;
        return this;
    }

    public PropagateClient build() {
        return new PollingPropagateClientV2Impl(env, URI.create(uri));
    }
}
