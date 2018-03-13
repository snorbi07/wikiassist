package com.norbertsram.wikiassist;

import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.business.CycleDetector;
import com.norbertsram.wikiassist.model.WikiPage;

import java.util.Arrays;
import java.util.List;

final public class WikiAssistCli {

    public static void main(String[] args) {
        System.out.println("WikiAssistCli started!");

        if (args.length == 0) {
            System.out.println("Please provide Simple Wiki page url(s)!");
            return;
        }

        final WikiPrefetch wikiPrefetch = new WikiPrefetch();
        final WikiAssistApi wikiAssistApi = new WikiAssistApi(wikiPrefetch.getPages(), wikiPrefetch.getReferences());

        final long totalExecutionTime = Arrays.stream(args).map(WikiAssistCli::extractTitle)
                .map(title -> process(title, wikiAssistApi))
                .mapToLong(Long::valueOf).sum();

        System.out.println("Total execution took: " + totalExecutionTime + "ms");

        System.out.println("WikiAssistCli completed!");
    }

    private static long process(String pageTitle, WikiAssistApi api) {
        System.out.println("Starting cycle search for: " + pageTitle);
        long startTime = System.currentTimeMillis();
        final CycleDetector cycleDetector = new CycleDetector(api);
        final List<List<WikiPage>> cycles = cycleDetector.findShortest(pageTitle);


        if (cycles.isEmpty()) {
            System.out.println("No cycles found.");
        }
        else {
            cycles.forEach(cycle -> System.out.println("Cycle: " + cycle.toString()));
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Cycle search for '" + pageTitle + "' took " + elapsedTime + "ms");

        return elapsedTime;
    }

    private static String extractTitle(String url) {
        final String SIMPLE_WIKI_URL_PREFIX = "https://simple.wikipedia.org/wiki/";
        if (!url.startsWith(SIMPLE_WIKI_URL_PREFIX)) {
            throw new IllegalArgumentException("Invalid Wiki URL format, got: " + url);
        }

        return url.substring(SIMPLE_WIKI_URL_PREFIX.length());
    }

}
