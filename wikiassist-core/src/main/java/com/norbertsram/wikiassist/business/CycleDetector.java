package com.norbertsram.wikiassist.business;

import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.model.WikiPage;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public final class CycleDetector {

    final WikiAssistApi api;

    public CycleDetector(WikiAssistApi api) {
        this.api = Objects.requireNonNull(api);
    }

    public boolean breadthFirstSearch(String pageTitle) {
        final int INITIAL_SIZE = 1024;
        Queue<WikiPage> nodesToVisit = new ArrayDeque <>(INITIAL_SIZE);
        nodesToVisit.addAll(api.referencedPages(pageTitle));

        boolean found = false;
        while (!nodesToVisit.isEmpty()) {
            WikiPage current = nodesToVisit.poll();

            if (current.getTitle().equals(pageTitle)) {
                found = true;
                break;
            }

            nodesToVisit.addAll(api.referencedPages(current));
        }

        return found;
    }

}
