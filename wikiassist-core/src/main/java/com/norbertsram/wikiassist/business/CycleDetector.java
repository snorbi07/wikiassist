package com.norbertsram.wikiassist.business;

import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.model.WikiPage;

import java.util.*;

public final class CycleDetector {

    private final WikiAssistApi api;
    private final WikiGraphTraversal wikiTraversal;

    private List<List<WikiPage>> detectedCycles;
    private int lengthOfShortestCycle = Integer.MAX_VALUE;

    public CycleDetector(WikiAssistApi api) {
        this.api = Objects.requireNonNull(api);
        this.wikiTraversal = new WikiGraphTraversal(api);

        detectedCycles = new ArrayList<>();
    }

    public List<List<WikiPage>> find(String pageTitle) {
        return find(pageTitle, false);
    }

    public List<List<WikiPage>> findShortest(String pageTitle) {
        return find(pageTitle, true);
    }

    private List<List<WikiPage>> find(String pageTitle, boolean depthCheck) {
        detectedCycles.clear();
        lengthOfShortestCycle = Integer.MAX_VALUE;

        final WikiPage wikiPage = api.byTitle(pageTitle);
        final int DEFAULT_SIZE = 2048;
        final Set<WikiPage> visited = new HashSet<>(DEFAULT_SIZE);
        final List<WikiPage> traversed = new ArrayList<>(DEFAULT_SIZE);

        wikiTraversal.depthFirstSearch(wikiPage, visited, traversed,
                (n, t) -> this.doTraverse(pageTitle, n, t, depthCheck));

        return detectedCycles;
    }

    boolean doTraverse(String pageTitle, WikiPage node, List<WikiPage> traversed, boolean depthCheck) {
        if (!node.getTitle().equals(pageTitle)) {
            return true;
        }
        final int depth = traversed.size();
        if (depthCheck && depth > lengthOfShortestCycle) {
            return false;
        }

        final List<WikiPage> cycle = new ArrayList<>(traversed);
        cycle.add(node);
        detectedCycles.add(cycle);

        lengthOfShortestCycle = lengthOfShortestCycle > cycle.size() ? cycle.size() : lengthOfShortestCycle;

        return false;
    }

}
