package com.norbertsram.wikiassist.crawler;

import com.norbertsram.wikiassist.model.WikipediaPage;

import java.util.Objects;

public class WikipediaPageHandler {

    private final String baseUrl;

    public WikipediaPageHandler(String baseUrl) {
        this.baseUrl = Objects.requireNonNull(baseUrl);
    }

    public boolean doVisit(String pageUrl) {
        // we only want to traverse Wikipedia pages belonging to a specific language
        return pageUrl.startsWith(baseUrl);
    }

    public void visited(WikipediaPage page) {
        System.out.println("Visited: " + page.toString());

    }
}
