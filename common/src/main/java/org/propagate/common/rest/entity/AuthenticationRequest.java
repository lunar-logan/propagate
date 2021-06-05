package org.propagate.common.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {
    @NotNull
    @Email
    private String principal;

    @NotBlank
    private String credential;

    @NotNull
    @Builder.Default
    private AuthenticationType authType = AuthenticationType.EMAIL_PASSWORD;
}
