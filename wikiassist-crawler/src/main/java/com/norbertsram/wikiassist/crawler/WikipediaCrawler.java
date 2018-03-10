package com.norbertsram.wikiassist.crawler;

import com.norbertsram.wikiassist.crawler.web.WikipediaPageCrawler;

public class WikipediaCrawler {

    public static void main(String[] args) {

        final WikipediaPageHandler pageHandler = new WikipediaPageHandler();
        final Crawler crawler = new WikipediaPageCrawler();

        System.out.println("Starting Wikipedia crawling...");

        crawler.crawl("https://en.wikipedia.org/wiki/Main_Page", pageHandler::visited);

        System.out.println("Finished!!!");
    }

}
