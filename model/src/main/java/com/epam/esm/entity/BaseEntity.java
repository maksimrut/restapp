package com.epam.esm.entity;

import java.io.Serializable;

/**
 * The type Base entity for all entities.
 *
 * @author Maksim Rutkouski
 */
public class BaseEntity implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
