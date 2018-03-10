package com.norbertsram.wikiassist.crawler.web;

import com.norbertsram.wikiassist.model.WikipediaPage;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *  Handles the instantiation and managing of crawler threads.
 */
final class CrawlExecutor {

    public static int NUMBER_OF_CRAWLERS = 8;

    public static void crawl(String pageUrl, Consumer<WikipediaPage> visited) throws Exception {
        crawl(pageUrl, visited, NUMBER_OF_CRAWLERS);
    }

    public static void crawl(String pageUrl, Consumer<WikipediaPage> visited, int numCrawlers) throws Exception {
        Objects.requireNonNull(pageUrl);
        if (numCrawlers <= 0) {
            throw new IllegalArgumentException("Number of crawlers must be greater than 1, got: " + numCrawlers);
        }

        final String tempDir = System.getProperty("java.io.tmpdir");
        final String crawlStorageFolder = tempDir + "/wikiassist/crawl";

        System.out.println(crawlStorageFolder);

        final CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        final PageFetcher pageFetcher = new PageFetcher(config);
        final RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        final RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        final CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(pageUrl);
        controller.startNonBlocking(() -> new HtmlCrawler(visited), numCrawlers);
    }

}
