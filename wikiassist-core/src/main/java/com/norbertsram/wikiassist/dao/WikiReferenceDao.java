package com.norbertsram.wikiassist.dao;

import com.norbertsram.wikiassist.dao.mapper.ReferenceEntryMapper;
import com.norbertsram.wikiassist.model.WikiReference;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface WikiReferenceDao {

    @SqlQuery("SELECT * FROM `references`")
    @RegisterRowMapper(ReferenceEntryMapper.class)
    List<WikiReference> fetchAll();

    @SqlQuery("SELECT * FROM `references` WHERE page_id = :id")
    @RegisterRowMapper(ReferenceEntryMapper.class)
    List<WikiReference> fetchById(@Bind("id") int pageId);

    @SqlQuery("SELECT * FROM `references` WHERE page_title = :title")
    @RegisterRowMapper(ReferenceEntryMapper.class)
    List<WikiReference> fetchByTitle(@Bind("title") String pageTitle);

}
