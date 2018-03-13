package com.norbertsram.wikiassist;

import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.dao.WikiReferenceDao;
import com.norbertsram.wikiassist.dao.WikiPageDao;
import com.norbertsram.wikiassist.model.WikiPage;
import com.norbertsram.wikiassist.model.WikiReference;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

// works at this scale, otherwise we would have to rely on DB magic all the way
class WikiPrefetch {

    static private final Logger LOG = LoggerFactory.getLogger(WikiAssistApi.class);

    private final Jdbi jdbi;
    private final List<WikiReference> references;
    private final List<WikiPage> pages;

    public WikiPrefetch() {
        // TODO: yeah, I know...
        jdbi = Jdbi.create("jdbc:mariadb://localhost:3306/simplewiki", "root", "");
        jdbi.installPlugin(new SqlObjectPlugin());

        LOG.info("Prefetching references...");
        references = Collections.unmodifiableList(fetchReferences());

        LOG.info("Prefetching Wiki pages...");
        pages = Collections.unmodifiableList(fetchPages());

        LOG.info("Prefetching completed!");
    }

    private List<WikiReference> fetchReferences() {
        return jdbi.withExtension(WikiReferenceDao.class, dao -> dao.fetchAll());
    }

    private List<WikiPage> fetchPages() {
        return jdbi.withExtension(WikiPageDao.class, dao -> dao.fetchAll());
    }

    public List<WikiReference> getReferences() {
        return references;
    }

    public List<WikiPage> getPages() {
        return pages;
    }
}
