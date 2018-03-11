package com.norbertsram.wikiassist.dao.mapper;

import com.norbertsram.wikiassist.model.ReferenceEntry;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class ReferenceEntryMapper implements RowMapper<ReferenceEntry> {

    @Override
    public ReferenceEntry map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new ReferenceEntry(
                rs.getInt("page_id"),
                rs.getString("page_title"),
                rs.getInt("page_reference_id"),
                rs.getString("page_reference_title")
        );
    }

}
