package com.norbertsram.wikiassist.model;

final public class WikiReference {

    private final int pageId;
    private final String pageTitle;
    private final int pageReferenceId;
    private final String pageReferenceTitle;

    public WikiReference(int pageId, String pageTitle, int pageReferenceId, String pageReferenceTitle) {
        this.pageId = pageId;
        this.pageTitle = pageTitle;
        this.pageReferenceId = pageReferenceId;
        this.pageReferenceTitle = pageReferenceTitle;
    }

    public int getPageId() {
        return pageId;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public int getPageReferenceId() {
        return pageReferenceId;
    }

    public String getPageReferenceTitle() {
        return pageReferenceTitle;
    }

}
