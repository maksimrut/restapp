package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The Tag service interface.
 * Specifies a set of methods to get and manage tags
 *
 * @author Maksim Rutkouski
 */
public interface TagService {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Tag> findAll();

    /**
     * Find tag by id.
     *
     * @param id the id
     * @return the optional with a tag object if it exists, otherwise the empty optional
     */
    Optional<Tag> findById(Long id);

    /**
     * Create tag.
     *
     * @param tag the tag object
     * @return the created tag
     * @throws ServiceException the service exception if this tag already exists in the database
     */
    Tag create(Tag tag) throws ServiceException;

    /**
     * Delete the tag by id.
     *
     * @param id the id
     */
    void delete(Long id);

    /**
     * boolean check if tag exists.
     *
     * @param tag the tag
     * @return the boolean
     */
    boolean exist(Tag tag);

    /**
     * long, find tag by name.
     *
     * @param name the name
     * @return the long
     * @throws ServiceException the service exception if this tag doesn't exist.
     */
    Long findByName(String name) throws ServiceException;
}
