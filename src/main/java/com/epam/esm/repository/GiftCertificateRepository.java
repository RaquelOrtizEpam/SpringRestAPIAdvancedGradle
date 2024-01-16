package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Transactional
public class GiftCertificateRepository implements CrudRepository<GiftCertificate> {
    private static final String INSERT = """
            INSERT INTO gift_certificate (name, description, price, create_date, last_update_date, duration_in_days) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String SELECT_BY_ID = """
            SELECT * FROM gift_certificate 
            WHERE id = ?
            """;
    private static final String SELECT_BY_TAG_ID = """
            SELECT gc.* FROM gift_certificate gc 
             JOIN gift_certificate_tag gct on gc.id = gct.gift_certificate_id 
             WHERE gct.tag_id = ?
             """;

    private static final String SELECT_BY_PART_OF_STRING = """
            SELECT * FROM gift_certificate 
            WHERE name LIKE ? 
                OR description LIKE ?
             """;
    private static final String UPDATE = """
            UPDATE gift_certificate 
            SET name = ?, description = ?, price = ?, last_update_date = ?, duration_in_days = ? 
            WHERE id = ?
            """;
    private static final String DELETE = """
            DELETE FROM gift_certificate 
            WHERE id = ?
            """;
    private static final String INSERT_ASSOCIATION = """
            INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) 
            VALUES (?, ?) 
            ON CONFLICT (gift_certificate_id, tag_id) DO NOTHING
            """;
    private static final String DELETE_ASSOCIATION = """
            DELETE FROM gift_certificate_tag 
            WHERE gift_certificate_id = ? AND tag_id = ?
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public GiftCertificate create(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{"id"});
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setDouble(3, entity.getPrice());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
            ps.setInt(6, entity.getDurationInDays());
            return ps;
        }, keyHolder);

        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue())
                .orElseThrow();
    }

    public void associateTags(Long giftCertificateId, List<Tag> tags) {
        tags.forEach(tag -> jdbcTemplate.update(INSERT_ASSOCIATION, giftCertificateId, tag.getId()));
    }

    public void disassociateTags(Long giftCertificateId, List<Tag> tags) {
        tags.forEach(tag -> jdbcTemplate.update(DELETE_ASSOCIATION, giftCertificateId, tag.getId()));
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        jdbcTemplate.update(UPDATE,
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)),
                entity.getDurationInDays(),
                entity.getId());

        return entity;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID, this::mapRowToGiftCertificate, id).stream().findFirst();
    }

    public List<GiftCertificate> findAllByTag(Tag tag) {
        return jdbcTemplate.query(SELECT_BY_TAG_ID, this::mapRowToGiftCertificate, tag.getId());
    }

    public List<GiftCertificate> searchByPartOfNameOrDescription(String searchQuery) {
        String searchParam = "%" + searchQuery + "%";
        return jdbcTemplate.query(SELECT_BY_PART_OF_STRING, this::mapRowToGiftCertificate, searchParam, searchParam);
    }

    @Override
    public void delete(GiftCertificate entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    public List<GiftCertificate> findGiftCertificatesWithTags(String tagName, String searchQuery, String sortBy, String sortType) {
        StringBuilder query = new StringBuilder("SELECT DISTINCT gc.* FROM gift_certificate gc");

        List<Object> parameters = new ArrayList<>();

        if (tagName != null) {
            query.append(" JOIN gift_certificate_tag gct ON gc.id = gct.gift_certificate_id");
            query.append(" JOIN tag t ON gct.tag_id = t.id WHERE t.name = ?");
            parameters.add(tagName);
        }

        if (searchQuery != null) {
            if (tagName != null) {
                query.append(" OR (");
            } else {
                query.append(" WHERE (");
            }
            query.append("LOWER(gc.name) LIKE ? OR LOWER(gc.description) LIKE ?)");
            parameters.add("%" + searchQuery.toLowerCase() + "%");
            parameters.add("%" + searchQuery.toLowerCase() + "%");
        }

        if (sortBy != null) {
            query.append(" ORDER BY ");
            String[] sortByArray = sortBy.split(",");
            String[] sortTypeArray = sortType.split(",");
            for (int i = 0; i < sortByArray.length; i++) {
                if (i > 0) {
                    query.append(", ");
                }
                query.append("gc.").append(sortByArray[i]);
                if ("desc".equalsIgnoreCase(sortTypeArray[i])) {
                    query.append(" DESC");
                } else {
                    query.append(" ASC");
                }
            }
        }

        return jdbcTemplate.query(query.toString(), this::mapRowToGiftCertificate, parameters.toArray());
    }

    private GiftCertificate mapRowToGiftCertificate(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong("id"));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getDouble("price"));
        giftCertificate.setCreateDate(rs.getObject("create_date", LocalDateTime.class));
        giftCertificate.setLastUpdateDate(rs.getObject("last_update_date", LocalDateTime.class));
        giftCertificate.setDurationInDays(rs.getInt("duration_in_days"));
        return giftCertificate;
    }
}
