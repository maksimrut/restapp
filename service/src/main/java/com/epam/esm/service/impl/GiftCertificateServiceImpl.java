package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Query;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository certificateRepository;
    private ModelMapper modelMapper;
    private TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepositoryImpl certificateRepository, TagService tagService, ModelMapper modelMapper) {
        this.certificateRepository = certificateRepository;
        this.tagService = tagService;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        this.modelMapper.typeMap(GiftCertificate.class, GiftCertificate.class).addMappings(mapper -> {
           mapper.skip(GiftCertificate::setCreateDate);
           mapper.skip(GiftCertificate::setLastUpdateDate);
           mapper.skip(GiftCertificate::setTags);
        });
    }

    @Override
    public List<GiftCertificate> findAll() {
        return certificateRepository.findAll();
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return certificateRepository.findById(id);
    }

    @Transactional
    @Override
    public GiftCertificate create(GiftCertificate certificate) throws ServiceException {
        certificate.setId(null);
        GiftCertificate createdCertificate = certificateRepository.create(certificate);
        if (certificate.getTags() != null) {
            addTags(createdCertificate.getId(), certificate.getTags());
            createdCertificate.getTags().addAll(certificate.getTags());
        }
        return createdCertificate;
    }

    @Override
    public void deleteById(Long id) {
        certificateRepository.deleteById(id);
    }

    @Transactional
    @Override
    public GiftCertificate update(Long id, GiftCertificate certificate) throws ServiceException {
        GiftCertificate updatedCertificate = certificateRepository.findById(id)
                .orElseThrow(ServiceException::new);
        modelMapper.map(certificate, updatedCertificate);
        updatedCertificate.setLastUpdateDate(LocalDateTime.now().withNano(0));
        certificateRepository.clearTags(id);
        if (certificate.getTags() != null) {
            addTags(id, certificate.getTags());
            updatedCertificate.getTags().addAll(certificate.getTags());
        }
        return certificateRepository.update(id, updatedCertificate);
    }

    @Override
    public List<GiftCertificate> findAllByParams(Query query) {
        return certificateRepository.findAllByParams(query);
    }

    @Override
    public void clearTags(Long giftCertificateId) {
        certificateRepository.clearTags(giftCertificateId);
    }

    private void addTags(Long giftCertificateId, Set<Tag> tags) throws ServiceException {
        for (Tag tag : tags) {
            if (!tagService.exist(tag)){
                tagService.create(tag);
            }
            Long tagId = tagService.findByName(tag.getName());
            certificateRepository.addTag(giftCertificateId, tagId);
        }
    }
}
