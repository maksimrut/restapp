package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Query;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.mapper.GiftCertificateExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private static final String SQL_FIND_ALL =
            "SELECT gift_certificates.id, gift_certificates.name, description, price, duration, createDate, lastUpdateDate, t.id AS tagId, t.name AS tagName " +
            "FROM gift_certificates " +
            "LEFT JOIN tags_certificates tc ON gift_certificates.id = tc.gift_certificate_id "+
            "LEFT JOIN tags t ON tc.tag_id = t.id";
    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL +
            " WHERE gift_certificates.id=?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM gift_certificates WHERE id=?";
    private static final String SQL_CREATE =
            "INSERT INTO gift_certificates(name, description, price, duration, createDate, lastUpdateDate)" +
            "VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE gift_certificates " +
            "SET name=?, description=?, price=?, duration=?, createDate=?, lastUpdateDate=? " +
            "WHERE id=?";
    private static final String SQL_FIND_ALL_TAGS =
            "SELECT tc.gift_certificate_id, tc.tag_id, tags.name FROM tags " +
            "JOIN tags_certificates tc ON tags.id = tc.tag_id " +
            "WHERE tc.gift_certificate_id=?";
    private static final String SQL_ADD_TAG =
            "INSERT INTO tags_certificates (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SQL_CLEAR_TAGS =
            "DELETE FROM tags_certificates WHERE gift_certificate_id=?";

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateExtractor extractor;

    @Autowired
    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate, GiftCertificateExtractor extractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.extractor = extractor;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, extractor);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, extractor, id)
                .stream()
                .findAny();
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        certificate.setCreateDate(LocalDateTime.now().withNano(0));
        certificate.setLastUpdateDate(certificate.getCreateDate());

        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, certificate.getName());
            statement.setString(2, certificate.getDescription());
            statement.setBigDecimal(3, certificate.getPrice());
            statement.setInt(4, certificate.getDuration());
            statement.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            statement.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            return statement;
        }, keyHolder);
        long certificateId;
        if (keyHolder.getKeys().size() > 1) {
            certificateId = ((Number) keyHolder.getKeys().get("id")).longValue();
        } else {
            certificateId = keyHolder.getKey().longValue();
        }
        certificate.setId(certificateId);
        return certificate;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public GiftCertificate update(Long id, GiftCertificate certificate) {
        jdbcTemplate.update(SQL_UPDATE, certificate.getName(),
                certificate.getDescription(), certificate.getPrice(),
                certificate.getDuration(), certificate.getCreateDate(),
                certificate.getLastUpdateDate(), id);
        return findById(id).get();
    }

    @Override
    public List<Tag> findCertificateTags(Long giftCertificateId) {
        return jdbcTemplate.query(SQL_FIND_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class), giftCertificateId);
    }

    @Override
    public void addTag(Long giftCertificateId, Long tagId) {
        jdbcTemplate.update(SQL_ADD_TAG, giftCertificateId, tagId);
    }

    @Override
    public void clearTags(Long giftCertificateId) {
        jdbcTemplate.update(SQL_CLEAR_TAGS, giftCertificateId);
    }

    @Override
    public List<GiftCertificate> findAllByParams(Query query) {
        return jdbcTemplate.query(query.buildSqlQuery(), extractor, query.getQueryParams());
    }
}
