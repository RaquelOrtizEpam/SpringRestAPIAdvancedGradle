package com.epam.esm.repository;

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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Transactional
public class TagRepository implements CrudRepository<Tag> {
    private static final String INSERT = """
            INSERT INTO tag (name) 
            VALUES (?)
            """;
    private static final String SELECT_BY_ID = """
            SELECT * FROM tag 
            WHERE id = ?
            """;
    private static final String SELECT_BY_NAME = """
            SELECT * FROM tag 
            WHERE name = ?
            """;
    private static final String UPDATE = """
            UPDATE tag 
            SET name = ? 
            WHERE id = ?
            """;
    private static final String DELETE = """
            DELETE FROM tag 
            WHERE id = ?
            """;
    private static final String SELECT_ALL_TAGS_BY_GIFT_ID = """
            SELECT * FROM tag 
            WHERE id IN (SELECT tag_id FROM gift_certificate_tag WHERE gift_certificate_id = ?)
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Tag create(Tag entity) {
        Optional<Tag> existingTag = findByName(entity.getName());
        if (existingTag.isPresent()) {
            return existingTag.get();
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{"id"});
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return entity;
    }

    @Override
    public Tag update(Tag entity) {
        jdbcTemplate.update(UPDATE,
                entity.getName(),
                entity.getId());

        return entity;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return jdbcTemplate.query(SELECT_BY_ID, this::mapRowToTag, id).stream().findFirst();
    }

    public Optional<Tag> findByName(String name) {
        return jdbcTemplate.query(SELECT_BY_NAME, this::mapRowToTag, name).stream().findFirst();
    }

    @Override
    public void delete(Tag entity) {
        jdbcTemplate.update(DELETE, entity.getId());
    }

    public List<Tag> findAllByGiftCertificateId(Long id) {
        return jdbcTemplate.query(SELECT_ALL_TAGS_BY_GIFT_ID, this::mapRowToTag, id);
    }

    private Tag mapRowToTag(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong("id"));
        tag.setName(rs.getString("name"));
        return tag;
    }
}
