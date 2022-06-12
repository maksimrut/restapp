package com.epam.esm.repository.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.repository.mapper.ColumnName.*;

@Component
public class GiftCertificateExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    private final GiftCertificateMapper certificateMapper;

    @Autowired
    public GiftCertificateExtractor(GiftCertificateMapper certificateMapper) {
        this.certificateMapper = certificateMapper;
    }

    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, GiftCertificate> giftCertificateMap = new HashMap<>();
        while (rs.next()) {
            Long key = rs.getLong(ID);
            giftCertificateMap.putIfAbsent(key, certificateMapper.mapRow(rs, rs.getRow()));
            Tag tag = new Tag();
            tag.setId(rs.getLong(TAG_ID));
            tag.setName(rs.getString(TAG_NAME));
            giftCertificateMap.get(key).addTag(tag);
        }
        return new ArrayList<>(giftCertificateMap.values());
    }
}
