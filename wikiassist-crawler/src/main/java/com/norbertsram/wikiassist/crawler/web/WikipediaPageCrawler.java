package com.norbertsram.wikiassist.crawler.web;

import com.norbertsram.wikiassist.crawler.Crawler;
import com.norbertsram.wikiassist.model.WikipediaPage;

import java.util.function.Consumer;
import java.util.function.Predicate;

final public class WikipediaPageCrawler implements Crawler {

    @Override
    public void crawl(String pageUrl, Predicate<String> doVisit, Consumer<WikipediaPage> visited) {
        try {
            CrawlExecutor.crawl(pageUrl, doVisit, visited);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
