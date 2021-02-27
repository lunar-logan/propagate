package org.propagate.client.spi;

import org.propagate.client.provider.http.PollingPropagateClientV2Impl;

import java.net.URI;

@Deprecated
public class LegacyPollingBasedPropagateClientBuilder {
    private final String env;
    private String uri;

    public LegacyPollingBasedPropagateClientBuilder(String env) {
        this.env = env;
    }

    public LegacyPollingBasedPropagateClientBuilder withConnectionUri(String uri) {
        this.uri = uri;
        return this;
    }

    public PropagateClient build() {
        return new PollingPropagateClientV2Impl(env, URI.create(uri));
    }
}
