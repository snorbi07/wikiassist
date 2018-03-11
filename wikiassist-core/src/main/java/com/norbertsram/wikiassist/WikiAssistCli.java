package com.norbertsram.wikiassist;

import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.model.ReferenceEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

final public class WikiAssistCli {

    private static final Logger LOG = LoggerFactory.getLogger(WikiAssistApi.class);


    public static void main(String[] args) {
        LOG.info("WikiAssistCli started!");
        final WikiAssistApi wikiAssistApi = new WikiAssistApi();

        final List<ReferenceEntry> entries = wikiAssistApi.getEntries();
        LOG.info("Loaded {} reference entries.", entries.size());

        LOG.info("WikiAssistCli completed!");
    }

}
