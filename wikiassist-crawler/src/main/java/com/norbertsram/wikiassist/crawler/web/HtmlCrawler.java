package com.norbertsram.wikiassist.crawler.web;

import com.norbertsram.wikiassist.model.WikipediaPage;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class only handles the crawling of Wikipedia HTML pages. Other media types are ignored.
 */
final class HtmlCrawler extends WebCrawler {

    private final Consumer<WikipediaPage> visited;

    public HtmlCrawler(Consumer<WikipediaPage> visited) {
        this.visited = Objects.requireNonNull(visited);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        final String href = url.getURL().toLowerCase();
        return WikipediaPage.isValidReferenceUrl(href);
    }

    @Override
    public void visit(Page page) {
        if (!(page.getParseData() instanceof HtmlParseData)) {
            // The traversal should filter out all non-HTML pages, thus if we enter here something unsupported happened.
            // This might lead to incorrect results thus further analysis is needed.
            // TODO(snorbi07): log warning
            System.err.println("Unsupported page type: " + page.getWebURL().getURL().toString());
            // Otherwise this shouldn't crash the application so we skip this entry.
            return;
        }

        final HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
        final Set<WebURL> links = htmlParseData.getOutgoingUrls();

        final Set<String> references =
                links.stream()
                        .map(link -> link.getURL())
                        .filter(WikipediaPage::isValidReferenceUrl)
                        .collect(Collectors.toCollection(HashSet::new));
        final String url = page.getWebURL().getURL();

        visited.accept(new WikipediaPage(url, references));
    }


}
