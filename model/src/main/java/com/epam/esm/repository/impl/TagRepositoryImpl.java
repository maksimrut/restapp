package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String SQL_FIND_ALL = "SELECT id, name FROM tags";
    private static final String SQL_FIND_BY_ID = "SELECT id, name FROM tags WHERE id=?";
    private static final String SQL_CREATE = "INSERT INTO tags(name) VALUES(?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM tags WHERE id=?";
    private static final String SQL_FIND_BY_NAME = "SELECT id FROM tags WHERE name=?";
    private static final String SQL_FIND_BY_CERTIFICATE_ID =
            "SELECT t.id, t.name FROM tags t " +
            "JOIN tags_certificates tc ON t.id = tc.tag_id " +
            "WHERE tc.gift_certificate_id = ?";
    private static final Long RETURN_VALUE = -1L;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream()
                .findAny();
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1, tag.getName());
                    return preparedStatement;
                }, keyHolder);
        long tagId;
        if (keyHolder.getKeys().size() > 1) {
            tagId = ((Number) keyHolder.getKeys().get("id")).longValue();
        } else {
            tagId = keyHolder.getKey().longValue();
        }
        tag.setId(tagId);
        return tag;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SQL_FIND_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream().
                findAny();
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_CERTIFICATE_ID, new BeanPropertyRowMapper<>(Tag.class), id);
    }
}
