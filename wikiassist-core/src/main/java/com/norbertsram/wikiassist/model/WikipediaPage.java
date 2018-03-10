package com.norbertsram.wikiassist.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

final public class WikipediaPage {

    private final static Pattern UNSUPPORTED_MEDIA_TYPES = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp4|zip|gz))$");
    private final static String EN_WIKIPEDIA_BASE_URL = "https://en.wikipedia.org";

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

    public static boolean isSupportedUrlType(String url) {
        // TODO(snorbi07): a more robust solution would be to match for types we are looking for, meaning HTML
        return !UNSUPPORTED_MEDIA_TYPES.matcher(url).matches();
    }

    private static boolean isWikipediaUrl(String url) {
        return url.startsWith(EN_WIKIPEDIA_BASE_URL);
    }

    public static boolean isValidReferenceUrl(String url, Predicate<String> customValidator) {
        return isSupportedUrlType(url) && customValidator.test(url);
    }

    public static boolean isValidReferenceUrl(String url) {
        return isValidReferenceUrl(url, WikipediaPage::isWikipediaUrl);
    }

}
