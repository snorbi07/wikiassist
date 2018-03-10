package com.norbertsram.wikiassist.crawler;

import com.norbertsram.wikiassist.model.WikipediaPage;

import java.util.function.Consumer;

public interface Crawler {

    void crawl(String pageUrl, Consumer<WikipediaPage> visited);

}
