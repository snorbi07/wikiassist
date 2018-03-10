package com.norbertsram.wikiassist.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

final public class WikipediaPage {

    final private String url; // the page we visited
    final private Set<String> references; // we want to have each reference only once

    public WikipediaPage(String url, Set<String> references) {
        this.url = Objects.requireNonNull(url);
        this.references = Objects.requireNonNull(references);
    }

    public String getUrl() {
        return url;
    }

    public Set<String> getReferences() {
        return Collections.unmodifiableSet(references);
    }

    @Override
    public String toString() {
        return "WikipediaPage{" +
                "url='" + url + '\'' +
                ", references=" + references +
                '}';
    }
}
