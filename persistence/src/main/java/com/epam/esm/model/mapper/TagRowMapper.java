package com.epam.esm.model.mapper;

import com.epam.esm.model.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TagRowMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) {
        try {
            Tag tag = new Tag();
            tag.setId(rs.getLong(DatabaseColumnName.TAG_ID));
            tag.setTitle(rs.getString(DatabaseColumnName.TAG_TITLE));
            return tag;
        } catch (SQLException e) {
            return null;
        }
    }
}
