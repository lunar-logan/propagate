package org.propagate.core.dao;

import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    void deleteById(ID id);
}
