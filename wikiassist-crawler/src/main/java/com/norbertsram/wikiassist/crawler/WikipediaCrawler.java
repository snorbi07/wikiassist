package com.norbertsram.wikiassist.crawler;

import com.norbertsram.wikiassist.crawler.web.WikipediaPageCrawler;

public class WikipediaCrawler {

    public static void main(String[] args) {

        final String EN_WIKIPEDIA_BASE_URL = "https://en.wikipedia.org";
        final WikipediaPageHandler pageHandler = new WikipediaPageHandler(EN_WIKIPEDIA_BASE_URL);
        final Crawler crawler = new WikipediaPageCrawler();

        System.out.println("Starting Wikipedia crawling...");

        crawler.crawl("https://en.wikipedia.org/wiki/Main_Page", pageHandler::doVisit, pageHandler::visited);

        System.out.println("Finished!!!");
    }

}
