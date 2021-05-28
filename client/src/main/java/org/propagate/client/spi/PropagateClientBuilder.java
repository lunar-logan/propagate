package org.propagate.client.spi;

import org.propagate.client.eval.FeatureFlagEvaluationService;
import org.propagate.client.eval.FeatureFlagEvaluationServiceImpl;
import org.propagate.client.featureflagclient.FeatureFlagClient;
import org.propagate.client.featureflagclient.RestFeatureFlagClientImpl;
import org.propagate.client.provider.http.PropagateHttpClient;
import org.propagate.client.tenant.TenantContext;
import org.propagate.client.transport.http.RestClient;

import java.util.Objects;

public class PropagateClientBuilder {
    private String baseUrl;
    private String environment;

    public PropagateClientBuilder baseUrl(String baseUrl) {
        this.baseUrl = Objects.requireNonNull(baseUrl);
        return this;
    }

    public PropagateClientBuilder environment(String env) {
        this.environment = Objects.requireNonNull(env);
        return this;
    }

    private RestClient provideNewRestClient() {
        return new RestClient();
    }

    public PropagateClient buildPollingClient() {
        final TenantContext tenantContext = new TenantContext(baseUrl, environment);
        final FeatureFlagClient featureFlagClient = new RestFeatureFlagClientImpl(tenantContext, provideNewRestClient());
        final FeatureFlagEvaluationService featureFlagEvaluationService = new FeatureFlagEvaluationServiceImpl();
        return new PropagateHttpClient(tenantContext, featureFlagClient, featureFlagEvaluationService);
    }
}