package com.norbertsram.wikiassist.dao.mapper;

import com.norbertsram.wikiassist.model.WikiPage;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class WikiPageMapper implements RowMapper<WikiPage> {

    @Override
    public WikiPage map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new WikiPage(
                rs.getInt("page_id"),
                rs.getString("page_title")
        );
    }

}
