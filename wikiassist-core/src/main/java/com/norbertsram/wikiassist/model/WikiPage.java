package com.norbertsram.wikiassist.model;

import java.util.Objects;

final public class WikiPage {

    private final int pageId;
    private final String title;

    public WikiPage(int pageId, String title) {
        this.pageId = pageId;
        this.title = title;
    }

    public int getPageId() {
        return pageId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final WikiPage wikiPage = (WikiPage) o;
        return getPageId() == wikiPage.getPageId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPageId());
    }

    @Override
    public String toString() {
        return "WikiPage{" +
                "pageId=" + pageId +
                ", title='" + title + '\'' +
                '}';
    }
}
