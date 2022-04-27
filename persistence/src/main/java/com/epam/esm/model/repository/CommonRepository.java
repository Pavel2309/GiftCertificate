package com.epam.esm.model.repository;

import java.util.List;
import java.util.Optional;

public interface CommonRepository<T, K> {

    List<T> findAll();

    Optional<T> findOne(K id);

    T create(T t);

    T update(T t);

    boolean delete(K id);

}
