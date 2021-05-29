package org.propagate.client.featureflagclient;

import com.fasterxml.jackson.core.type.TypeReference;
import org.propagate.client.tenant.TenantContext;
import org.propagate.client.transport.http.RestClient;
import org.propagate.common.util.Either;
import org.propagate.common.rest.entity.FeatureFlagRestEntity;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public class RestFeatureFlagClientImpl implements FeatureFlagClient {
    private static final String FEATURE_FLAGS_PATH = "/api/v1/featureflag/";

    private final TenantContext tenantContext;
    private final RestClient restClient;

    public RestFeatureFlagClientImpl(TenantContext tenantContext, RestClient restClient) {
        this.tenantContext = Objects.requireNonNull(tenantContext);
        this.restClient = Objects.requireNonNull(restClient);
    }

    @Override
    public Either<List<FeatureFlagRestEntity>> getAllFeatureFlags() {
        URI apiUrl = URI.create(tenantContext.getHost()).resolve(FEATURE_FLAGS_PATH);
        return restClient.get(apiUrl, new TypeReference<>() {
        });
    }
}
