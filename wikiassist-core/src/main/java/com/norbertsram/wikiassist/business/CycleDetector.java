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
        return find(pageTitle, false, false);
    }

    public Optional<List<WikiPage>> findFirst(String pageTitle) {
        final List<List<WikiPage>> cycles = find(pageTitle, false, true);
        if (cycles.isEmpty()) return Optional.empty();
        return Optional.of(cycles.get(0));
    }

    public List<List<WikiPage>> findShortest(String pageTitle) {
        return find(pageTitle, true, false);
    }

    private List<List<WikiPage>> find(String pageTitle, boolean depthCheck, boolean findSingle) {
        detectedCycles.clear();
        lengthOfShortestCycle = Integer.MAX_VALUE;

        final WikiPage wikiPage = api.byTitle(pageTitle);
        final int DEFAULT_SIZE = 2048;
        final Set<WikiPage> visited = new HashSet<>(DEFAULT_SIZE);
        final List<WikiPage> traversed = new ArrayList<>(DEFAULT_SIZE);

        wikiTraversal.depthFirstSearch(wikiPage, visited, traversed,
                (n, t) -> this.doTraverse(pageTitle, n, t, depthCheck, findSingle));

        return detectedCycles;
    }

    boolean doTraverse(String pageTitle, WikiPage node, List<WikiPage> traversed, boolean depthCheck, boolean findSingle) {
        if (findSingle && !detectedCycles.isEmpty()) {
            return false;
        }
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
