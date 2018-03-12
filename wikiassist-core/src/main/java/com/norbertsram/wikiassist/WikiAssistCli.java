package com.norbertsram.wikiassist;

import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.business.CycleDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class WikiAssistCli {

    private static final Logger LOG = LoggerFactory.getLogger(WikiAssistApi.class);


    public static void main(String[] args) {
        LOG.info("WikiAssistCli started!");


        final WikiPrefetch wikiPrefetch = new WikiPrefetch();
        final WikiAssistApi wikiAssistApi = new WikiAssistApi(wikiPrefetch.getPages(), wikiPrefetch.getReferences());

        final CycleDetector cycleDetector = new CycleDetector(wikiAssistApi);
        final String pageTitle = "Engineering";
        final boolean cycleFound = cycleDetector.breadthFirstSearch(pageTitle);
        LOG.info("Cycle detect status: {}, for page title: {}", cycleFound, pageTitle);

        LOG.info("WikiAssistCli completed!");
    }

}
