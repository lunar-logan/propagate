package org.propagate.client;

import org.propagate.query.DefaultQueryFactoryImpl;

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

    public HighLevelPropagateClient build() {
        return new HighLevelPropagateClient(env, new PropagateClientImpl(uri, new DefaultExpandedFeatureFlagMapper(new DefaultQueryFactoryImpl())));
    }
}
