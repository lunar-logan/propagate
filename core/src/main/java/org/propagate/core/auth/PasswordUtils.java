package org.propagate.core.auth;

import lombok.SneakyThrows;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

public final class PasswordUtils {
    private static final String HMAC_SHA512 = "HmacSHA512";

    private PasswordUtils() {
    }

    @SneakyThrows
    public static String hash(String plaintext, String salt) {
        Objects.requireNonNull(plaintext);

        final Mac mac = Mac.getInstance(HMAC_SHA512);
        final SecretKeySpec keySpec = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), HMAC_SHA512);
        mac.init(keySpec);
        byte[] macData = mac.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        // Can either base64 encode or put it right into hex
        return String.format("%s:%s", salt, Base64.getEncoder().encodeToString(macData));
    }

    @SneakyThrows
    public static boolean check(String plaintext, String hashed) {
        Objects.requireNonNull(plaintext);
        Objects.requireNonNull(hashed);

        int colon = hashed.indexOf(':');
        if (colon < 0) {
            throw new IllegalArgumentException("Invalid hashed password");
        }

        return hash(plaintext, hashed.substring(0, colon)).equals(hashed);
    }

    @SneakyThrows
    public static String genSalt() {
        byte[] key = new byte[32];
        SecureRandom rng = new SecureRandom();
        rng.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
