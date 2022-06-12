package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Query;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Gift certificate repository.
 *
 * @author Maksim Rutkouski
 */
@Repository
public interface GiftCertificateRepository extends BaseRepository<Long, GiftCertificate> {

    /**
     * Update gift certificate.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    GiftCertificate update(Long id, GiftCertificate giftCertificate);

    /**
     * Find all tags by certificate id.
     *
     * @param giftCertificateId the gift certificate id
     * @return the list
     */
    List<Tag> findCertificateTags(Long giftCertificateId);

    /**
     * Add tag to the certificate.
     *
     * @param giftCertificateId the gift certificate id
     * @param tagId             the tag id
     */
    void addTag(Long giftCertificateId, Long tagId);

    /**
     * Clear tags.
     *
     * @param giftCertificateId the gift certificate id
     */
    void clearTags(Long giftCertificateId);

    /**
     * Find all certificates by params list with query.
     *
     * @param query the query
     * @return the list
     */
    List<GiftCertificate> findAllByParams(Query query);
}
