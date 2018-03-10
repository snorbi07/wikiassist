package com.norbertsram.wikiassist.crawler;

import com.norbertsram.wikiassist.model.WikipediaPage;

public class WikipediaPageHandler {

    public void visited(WikipediaPage page) {
        System.out.println("Visited: " + page.toString());

    }
}
