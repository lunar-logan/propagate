package org.propagate.core.auth;

public class AuthenticatorFactory {
    private final Authenticator emailPwBased;

    public AuthenticatorFactory(Authenticator emailPwBased) {
        this.emailPwBased = emailPwBased;
    }

    public Authenticator getInstance(final String type) {
        return switch (type) {
            case "EMAIL_PASSWORD" -> emailPwBased;
            default -> throw new IllegalArgumentException("Unknown type \"" + type + "\"");
        };
    }
}
