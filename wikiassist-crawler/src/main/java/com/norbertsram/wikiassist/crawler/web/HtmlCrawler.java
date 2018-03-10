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
 * The actual behaviour is provided through the callback functions
 */
final class HtmlCrawler extends WebCrawler {

    private final static Pattern UNSUPPORTED_MEDIA_TYPES = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");

    private final Predicate<String> doVisit;
    private final Consumer<WikipediaPage> visited;

    public HtmlCrawler(Predicate<String> doVisit, Consumer<WikipediaPage> visited) {
        this.visited = Objects.requireNonNull(visited);
        this.doVisit = Objects.requireNonNull(doVisit);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        final String href = url.getURL().toLowerCase();
        // FIXME(snorbi07): a more robust solution would be to match for types we are looking for, meaning HTML
        return !UNSUPPORTED_MEDIA_TYPES.matcher(href).matches() && doVisit.test(href);
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
                        // ignore the links that contain unsupported types
                        .filter(link -> !UNSUPPORTED_MEDIA_TYPES.matcher(link.getURL()).matches())
                        // also ignore the ones that match for the provided custom logic
                        .filter(link -> doVisit.test(link.getURL()))
                        .map(link -> link.getURL())
                        .collect(Collectors.toCollection(HashSet::new));
        final String url = page.getWebURL().getURL();

        visited.accept(new WikipediaPage(url, references));
    }


}
