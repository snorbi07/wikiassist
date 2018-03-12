package com.norbertsram.wikiassist.dao;

import com.norbertsram.wikiassist.dao.mapper.WikiPageMapper;
import com.norbertsram.wikiassist.model.WikiPage;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface WikiPageDao {

    @SqlQuery("SELECT page_id, page_title FROM `page` WHERE page_namespace = 0")
    @RegisterRowMapper(WikiPageMapper.class)
    List<WikiPage> fetchAll();

}
