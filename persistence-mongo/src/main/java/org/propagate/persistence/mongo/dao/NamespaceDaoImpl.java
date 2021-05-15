package org.propagate.persistence.mongo.dao;

import lombok.AllArgsConstructor;
import org.propagate.common.dao.NamespaceDao;
import org.propagate.persistence.mongo.entity.NamespaceMongoEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class NamespaceDaoImpl implements NamespaceDao {
    private final NamespaceMongoDao namespaceMongoDao;

    @Override
    public String save(String entity) {
        final NamespaceMongoEntity nsMongoEntity = namespaceMongoDao.save(NamespaceMongoEntity.builder()
                .value(entity)
                .id(entity)
                .created(new Date())
                .build());
        return nsMongoEntity.getValue();
    }

    @Override
    public Optional<String> findById(String s) {
        return namespaceMongoDao.findById(s)
                .map(NamespaceMongoEntity::getValue);
    }

    @Override
    public List<String> findAll() {
        return namespaceMongoDao.findAll()
                .stream()
                .map(NamespaceMongoEntity::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAll(int page, int size) {
        return namespaceMongoDao.findAll(PageRequest.of(page, size))
                .map(NamespaceMongoEntity::getValue)
                .toList();
    }

    @Override
    public void delete(String entity) {
        namespaceMongoDao.deleteById(entity);
    }

    @Override
    public void deleteById(String s) {
        namespaceMongoDao.deleteById(s);
    }
}
