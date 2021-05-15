package org.propagate.server.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface NamespaceService {
    String createNamespace(@NotBlank String namespace);

    @NotNull List<String> getAllNamespaces();

    void deleteNamespace(@NotBlank String namespace);
}
