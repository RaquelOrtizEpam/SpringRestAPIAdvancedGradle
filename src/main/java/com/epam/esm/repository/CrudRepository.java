package com.epam.esm.repository;

import java.util.Optional;

public interface CrudRepository<T> {
    T create(T entity);

    T update(T entity);

    Optional<T> findById(Long id);

    void delete(T entity);
}
