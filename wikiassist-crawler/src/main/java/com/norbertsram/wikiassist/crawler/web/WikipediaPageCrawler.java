package com.norbertsram.wikiassist.crawler.web;

import com.norbertsram.wikiassist.crawler.Crawler;
import com.norbertsram.wikiassist.model.WikipediaPage;

import java.util.function.Consumer;

final public class WikipediaPageCrawler implements Crawler {

    @Override
    public void crawl(String pageUrl, Consumer<WikipediaPage> visited) {
        try {
            CrawlExecutor.crawl(pageUrl, visited);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
