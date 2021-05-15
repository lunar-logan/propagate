package org.propagate.client.tenant;

import java.util.Objects;

public class TenantContext {
    private final String host;
    private final String environment;

    public TenantContext(String host, String environment) {
        this.host = Objects.requireNonNull(host);
        this.environment = Objects.requireNonNull(environment);
    }

    public String getHost() {
        return host;
    }

    public String getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        return "TenantContext{" +
                "propagateHost='" + host + '\'' +
                ", environment='" + environment + '\'' +
                '}';
    }
}
