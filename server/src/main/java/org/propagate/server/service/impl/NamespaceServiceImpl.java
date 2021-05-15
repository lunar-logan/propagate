package org.propagate.server.service.impl;

import lombok.AllArgsConstructor;
import org.propagate.common.dao.NamespaceDao;
import org.propagate.server.service.NamespaceService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@AllArgsConstructor
public class NamespaceServiceImpl implements NamespaceService {
    private final NamespaceDao namespaceDao;

    @Override
    public String createNamespace(@NotBlank String namespace) {
        return namespaceDao.save(namespace);
    }

    @Override
    public @NotNull List<String> getAllNamespaces() {
        return namespaceDao.findAll();
    }

    @Override
    public void deleteNamespace(@NotBlank String namespace) {
        namespaceDao.delete(namespace);
    }
}
