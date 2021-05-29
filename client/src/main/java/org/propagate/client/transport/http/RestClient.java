package org.propagate.client.transport.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.propagate.common.util.Either;

import java.net.ConnectException;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public class RestClient {
    private final HttpClient client;
    private final CircuitBreaker<Object> breaker;
    private final ObjectMapper mapper;

    public RestClient(HttpClient client, CircuitBreaker<Object> breaker, ObjectMapper mapper) {
        this.client = Objects.requireNonNull(client);
        this.breaker = Objects.requireNonNull(breaker);
        this.mapper = Objects.requireNonNull(mapper);
    }

    public RestClient() {
        this(
                HttpClientBuilder.create().build(),
                new CircuitBreaker<>()
                        .handle(ConnectException.class)
                        .withFailureThreshold(3, 10)
                        .withSuccessThreshold(5)
                        .withDelay(Duration.ofMinutes(1)),
                new ObjectMapper());
    }

    public <V> Either<V> get(URI uri, Map<String, String> headers, Class<V> vClass) {
        final HttpGet get = buildGetRequest(uri, headers);
        try {
            byte[] body = EntityUtils.toByteArray(execute(get).getEntity());
            return Either.right(mapper.readValue(body, vClass));
        } catch (Exception ex) {
            return Either.left(ex);
        }
    }

    public <V> Either<V> get(URI uri, Map<String, String> headers, TypeReference<V> ref) {
        final HttpGet get = buildGetRequest(uri, headers);
        try {
            byte[] body = EntityUtils.toByteArray(execute(get).getEntity());
            return Either.right(mapper.readValue(body, ref));
        } catch (Exception ex) {
            return Either.left(ex);
        }
    }

    public <V> Either<V> get(URI uri, Class<V> vClass) {
        return get(uri, null, vClass);
    }

    public <V> Either<V> get(URI uri, TypeReference<V> ref) {
        return get(uri, null, ref);
    }

    private HttpGet buildGetRequest(URI uri, Map<String, String> headers) {
        HttpGet get = new HttpGet(uri);
        if (headers != null) {
            headers.forEach(get::addHeader);
        }
        return get;
    }

    private HttpResponse execute(HttpUriRequest request) {
        return Failsafe.with(breaker).get(() -> {
            return client.execute(request);
        });
    }
}
