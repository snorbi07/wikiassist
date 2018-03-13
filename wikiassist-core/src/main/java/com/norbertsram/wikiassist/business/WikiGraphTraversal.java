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

    void depthFirstSearch(WikiPage page, List<WikiPage> traversed, BiPredicate<WikiPage, List<WikiPage>> doVisit) {
        traversed.add(page);

        System.out.println("At: " + page.getTitle() + ", through: " + traversed.toString());

        final List<WikiPage> referencedPages = api.referencedPages(page);
        referencedPages.stream()
                .filter(ref -> doVisit.test(ref, traversed) && !traversed.contains(ref))
                .forEach(ref -> depthFirstSearch(ref, new ArrayList<>(traversed), doVisit));
    }

    void depthFirstSearch(WikiPage page, List<WikiPage> traversed) {
        depthFirstSearch(page, traversed, (r, t) -> true);
    }

}
