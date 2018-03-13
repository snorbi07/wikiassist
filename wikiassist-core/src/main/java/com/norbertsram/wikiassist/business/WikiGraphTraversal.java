package com.norbertsram.wikiassist.business;

import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.model.WikiPage;

import java.util.*;
import java.util.function.BiPredicate;

final class WikiGraphTraversal {

    final WikiAssistApi api;

    public WikiGraphTraversal(WikiAssistApi api) {
        this.api = Objects.requireNonNull(api);
    }

    void depthFirstSearch(WikiPage page, Set<WikiPage> visited, List<WikiPage> traversed, BiPredicate<WikiPage, List<WikiPage>> doVisit) {
        visited.add(page);
        traversed.add(page);

        final List<WikiPage> referencedPages = api.referencedPages(page);
        referencedPages.stream()
                .filter(ref -> doVisit.test(ref, traversed) && !visited.contains(ref))
                .forEach(ref -> depthFirstSearch(ref, visited, new ArrayList<>(traversed), doVisit));
    }

}
