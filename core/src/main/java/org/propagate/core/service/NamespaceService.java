package org.propagate.core.service;

import java.util.List;

public interface NamespaceService {
    String createNamespace(String namespace);

    List<String> getAllNamespaces();

    void deleteNamespace(String namespace);
}
