package com.epam.esm.repository.impl;

import com.epam.esm.config.DevConfig;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(DevConfig.class)
@ActiveProfiles("dev")
class TagRepositoryImplTest {

    @Autowired
    private TagRepositoryImpl tagRepository;

    @Test
    void findAll() {
        List<Tag> tags = tagRepository.findAll();
        int expected = 5;
        int actual = tags.size();
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        Optional<Tag> tagOptional = tagRepository.findById(1L);
        assertNotNull(tagOptional.get());
    }

    @Test
    void create() {
        Tag newTag = new Tag();
        newTag.setName("seventh");
        Tag dbTag = tagRepository.create(newTag);
        assertNotNull(dbTag.getId());
    }

    @Test
    void deleteById() {
        tagRepository.deleteById(4L);
        Optional<Tag> tag = tagRepository.findById(4L);
        Boolean expected = Boolean.TRUE;
        Boolean actual = tag.isEmpty();
        assertEquals(expected, actual);
    }

    @Test
    void findByName() {
        Tag tag = tagRepository.findByName("first").get();
        long expected = 1L;
        long actual = tag.getId();
        assertEquals(expected, actual);
    }

    @Test
    void findAllByGiftCertificateId() {
        List<Tag> tags = tagRepository.findAllByGiftCertificateId(1L);
        int expected = 2;
        int actual = tags.size();
        assertEquals(expected, actual);
    }
}