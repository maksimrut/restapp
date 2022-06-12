package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CustomNotFoundException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Tag REST API controller.
 *
 * @author Maksim Rutkouski
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    /**
     * Get all tags.
     *
     * @return all available tags
     */
    @GetMapping
    public List<Tag> findAll() {
        return service.findAll();
    }

    /**
     * Get a tag by id.
     *
     * @param id the tag id
     * @return found tag, otherwise error response with 40401 status code
     */
    @GetMapping("/{id}")
    public Tag findById(@PathVariable("id") Long id) {
        return service.findById(id).orElseThrow(() -> new CustomNotFoundException(id));
    }

    /**
     * Create a tag.
     *
     * @param tag the tag json object
     * @return created tag
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag create(@RequestBody @Valid Tag tag) {
        try {
            return service.create(tag);
        } catch (ServiceException e) {
            throw new ResourceDuplicateException("name", tag.getName());
        }
    }

    /**
     * Delete a tag.
     *
     * @param id the tag id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
