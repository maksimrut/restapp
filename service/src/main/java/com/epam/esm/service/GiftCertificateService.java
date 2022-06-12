package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Query;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate service.
 *
 * @author Maksim Rutkouski
 */
public interface GiftCertificateService {

    /**
     * Find all certificates list.
     *
     * @return the list of certificates
     */
    List<GiftCertificate> findAll();

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional with a certificate object if it exists, otherwise the empty optional
     */
    Optional<GiftCertificate> findById(Long id);

    /**
     * Create or save gift certificate.
     *
     * @param certificate the certificate
     * @return the created certificate
     * @throws ServiceException the service exception
     */
    GiftCertificate create(GiftCertificate certificate) throws ServiceException;

    /**
     * Delete certificate by id.
     *
     * @param id the id
     */
    void deleteById(Long id);

    /**
     * Update gift certificate.
     *
     * @param id          the id
     * @param certificate the certificate
     * @return the gift certificate
     * @throws ServiceException the service exception if the certificate with id doesn't exist.
     */
    GiftCertificate update(Long id, GiftCertificate certificate) throws ServiceException;

    /**
     * Find all certificate by query params.
     *
     * @param query the query
     * @return the list of found certificates
     */
    List<GiftCertificate> findAllByParams(Query query);

    /**
     * Clear tags.
     *
     * @param giftCertificateId the gift certificate id
     */
    void clearTags(Long giftCertificateId);
}
