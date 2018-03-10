package com.norbertsram.wikiassist.crawler;

import com.norbertsram.wikiassist.model.WikipediaPage;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Crawler {

    void crawl(String pageUrl, Predicate<String> doVisit, Consumer<WikipediaPage> visited);

}
