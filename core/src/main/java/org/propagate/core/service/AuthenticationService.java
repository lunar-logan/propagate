package org.propagate.core.service;

import org.propagate.common.rest.entity.AuthenticationRequest;
import org.propagate.common.rest.entity.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
