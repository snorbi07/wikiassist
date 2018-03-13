package com.norbertsram.wikiassist.business;

import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.model.WikiPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class CycleDetector {

    private final WikiAssistApi api;
    private final WikiGraphTraversal wikiTraversal;

    private List<List<WikiPage>> detectedCycles;

    public CycleDetector(WikiAssistApi api) {
        this.api = Objects.requireNonNull(api);
        this.wikiTraversal = new WikiGraphTraversal(api);

        detectedCycles = new ArrayList<>();
    }

    public List<List<WikiPage>> search(String pageTitle) {
        detectedCycles.clear();

        final WikiPage wikiPage = api.byTitle(pageTitle);
        final int DEFAULT_DEPTH = 256;
        final List<WikiPage> visited = new ArrayList<>(DEFAULT_DEPTH);

        wikiTraversal.depthFirstSearch(wikiPage, visited,
                (node, traversed) -> this.doTraverse(pageTitle, node, traversed));

        return detectedCycles;
    }

    boolean doTraverse(String pageTitle, WikiPage node, List<WikiPage> traversed) {
        if (!node.getTitle().equals(pageTitle)) {
            return true;
        }
        System.out.println("Found it: " + traversed.toString());

        final List<WikiPage> cycle = new ArrayList<>(traversed);
        cycle.add(node);
        detectedCycles.add(cycle);
        return false;
    }

}
