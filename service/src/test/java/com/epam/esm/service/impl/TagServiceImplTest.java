package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepositoryImpl tagRepository;

    @Test
    void findAll() {
        List<Tag> tagsFromRepository = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("first");
        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("second");
        tagsFromRepository.add(tag1);
        tagsFromRepository.add(tag2);
        Mockito.when(tagRepository.findAll()).thenReturn(tagsFromRepository);
        List<Tag> tagsFromService = tagService.findAll();
        Assertions.assertEquals(tagsFromRepository, tagsFromService);
    }

    @Test
    void findById() {
        Tag tagFromRepository = new Tag();
        tagFromRepository.setId(1L);
        tagFromRepository.setName("first");
        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.of(tagFromRepository));
        Optional<Tag> tagFromService = tagService.findById(1L);
        Assertions.assertEquals(Optional.of(tagFromRepository), tagFromService);
        Mockito.verify(tagRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void create() throws ServiceException {
        Tag createdTag = new Tag();
        createdTag.setId(1L);
        createdTag.setName("first");
        Mockito.when(tagRepository.create(createdTag)).thenReturn(createdTag);
        Tag tagFromService = tagService.create(createdTag);
        Assertions.assertEquals(createdTag, tagFromService);
    }

    @Test
    void delete() {
        tagService.delete(777L);
        Mockito.verify(tagRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void findByNameNegative() {
        String name = "tag";
        Mockito.when(tagRepository.findByName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(ServiceException.class, () -> tagService.findByName(name));
        Mockito.verify(tagRepository, Mockito.times(1)).findByName(name);
    }
}
