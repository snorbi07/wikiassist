package com.norbertsram.wikiassist.api;

import com.norbertsram.wikiassist.model.WikiReference;
import com.norbertsram.wikiassist.model.WikiPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

final public class WikiAssistApi {

    static private final Logger LOG = LoggerFactory.getLogger(WikiAssistApi.class);

    private final Map<WikiPage, List<WikiPage>> adjacencyList;

    private final Map<String, WikiPage> byTitle;

    public WikiAssistApi(List<WikiPage> pages, List<WikiReference> references) {
        LOG.info("WikiAssist bootstrapping...");
        adjacencyList = Collections.unmodifiableMap(toAdjacencyList(references));
        byTitle = Collections.unmodifiableMap(toTitleMap(pages));
        LOG.info("WikiAssist bootstrapping completed!");
    }

    // By design we return an empty reference list for not existing pages
    public List<WikiPage> referencedPages(String pageTitle) {
        final WikiPage wikiPage = byTitle(pageTitle);
        return referencedPages(wikiPage);
    }

    public WikiPage byTitle(String pageTitle) {
        // Wikipedia replaces empty spaces with underscores in title names
        final String wikiPageTitleRepresentation = pageTitle.replaceAll(" ", "_");
        return byTitle.get(wikiPageTitleRepresentation);
    }

    public List<WikiPage> referencedPages(WikiPage page) {
        // If a page does not have references it won't be in references adjacency list,
        // thus we return an empty list (no references)
        return adjacencyList.getOrDefault(page, Collections.emptyList());
    }

    private Map<WikiPage, List<WikiPage>> toAdjacencyList(List<WikiReference> entries) {
        return entries.stream().collect(
                groupingBy(
                        ref -> new WikiPage(ref.getPageId(), ref.getPageTitle()),
                        mapping(ref -> new WikiPage(ref.getPageReferenceId(), ref.getPageReferenceTitle()),
                                collectingAndThen(toList(), Collections::unmodifiableList)))
        );
    }

    private Map<String, WikiPage> toTitleMap(List<WikiPage> pages) {
        return pages.stream().collect(Collectors.toMap(WikiPage::getTitle, Function.identity()));
    }

}
