package org.propagate.common.dao;

import java.util.List;
import java.util.Optional;

public interface CrudOperations<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    List<T> findAll(int page, int size);
    void delete(T entity);
    void deleteById(ID id);
}
