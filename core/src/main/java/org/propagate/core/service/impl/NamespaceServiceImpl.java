package org.propagate.core.service.impl;

import org.propagate.common.dao.NamespaceDao;
import org.propagate.core.service.NamespaceService;

import java.util.List;

public class NamespaceServiceImpl implements NamespaceService {
    private final NamespaceDao namespaceDao;

    public NamespaceServiceImpl(NamespaceDao namespaceDao) {
        this.namespaceDao = namespaceDao;
    }

    @Override
    public String createNamespace(String namespace) {
        return namespaceDao.save(namespace);
    }

    @Override
    public List<String> getAllNamespaces() {
        return namespaceDao.findAll();
    }

    @Override
    public void deleteNamespace(String namespace) {
        namespaceDao.delete(namespace);
    }
}
