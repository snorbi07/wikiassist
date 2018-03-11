package com.norbertsram.wikiassist.api;

import com.norbertsram.wikiassist.dao.ReferenceDao;
import com.norbertsram.wikiassist.model.ReferenceEntry;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

final public class WikiAssistApi {

    static private final Logger LOG = LoggerFactory.getLogger(WikiAssistApi.class);

    private final Jdbi jdbi;

    public List<ReferenceEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    private final List<ReferenceEntry> entries;

    public WikiAssistApi() {
        LOG.info("WikiAssist bootstrapping...");

        jdbi = Jdbi.create("jdbc:mariadb://localhost:3306/simplewiki", "root", "");
        jdbi.installPlugin(new SqlObjectPlugin());

        LOG.info("Prefetching references...");
        entries = fetchReferences();

        LOG.info("WikiAssist bootstrapping completed!");
    }

    private List<ReferenceEntry> fetchReferences() {
        return jdbi.withExtension(ReferenceDao.class, dao -> dao.fetchAll());
    }

}
