package com.epam.esm.repository.mapper;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.epam.esm.repository.mapper.ColumnName.*;

/**
 * The type Gift certificate mapper implements springframework
 * RowMapper and used for ResultSet rows mapping.
 *
 * @author Maksim Rutkouski
 */
@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong(ID));
        certificate.setName(rs.getString(NAME));
        certificate.setDescription(rs.getString(DESCRIPTION));
        certificate.setPrice(rs.getBigDecimal(PRICE));
        certificate.setDuration(rs.getInt(DURATION));
        certificate.setCreateDate(LocalDateTime.parse(rs.getString(CREATE_DATE), FORMATTER));
        certificate.setLastUpdateDate(LocalDateTime.parse(rs.getString(LAST_UPDATE_DATE), FORMATTER));
        return certificate;
    }
}
