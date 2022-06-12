package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base repository.
 *
 * @param <T> the type parameter
 * @param <E> the type parameter
 *
 * @author Maksim Rutkouski
 */
public interface BaseRepository<T, E> {

    /**
     * Find all entities.
     *
     * @return the list
     */
    List<E> findAll();

    /**
     * Find entity by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<E> findById(T id);

    /**
     * Create entity.
     *
     * @param entity the entity
     * @return the e
     */
    E create(E entity);

    /**
     * Delete entity by id.
     *
     * @param id the id
     */
    void deleteById(T id);
}
