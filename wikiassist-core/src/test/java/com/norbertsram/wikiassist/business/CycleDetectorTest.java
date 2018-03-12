package com.norbertsram.wikiassist.business;


import com.norbertsram.wikiassist.api.WikiAssistApi;
import com.norbertsram.wikiassist.model.WikiPage;
import com.norbertsram.wikiassist.model.WikiReference;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class CycleDetectorTest {


    private WikiAssistApi api;

    @Before
    public void init() {
        List<WikiPage> pages = new ArrayList<>();
        List<WikiReference> references = new ArrayList<>();

        WikiPage page0 = new WikiPage(0, "A");
        WikiPage page1 = new WikiPage(1, "B");
        WikiPage page2 = new WikiPage(2, "C");
        WikiPage page3 = new WikiPage(3, "D");
        WikiPage page4 = new WikiPage(4, "E");
        WikiPage page5 = new WikiPage(5, "F");
        WikiPage page6 = new WikiPage(6, "G");
        WikiPage page7 = new WikiPage(7, "H");
        WikiPage page8 = new WikiPage(8, "I");
        WikiPage page9 = new WikiPage(9, "J");
        pages.add(page0);
        pages.add(page1);
        pages.add(page2);
        pages.add(page3);
        pages.add(page4);
        pages.add(page5);
        pages.add(page6);
        pages.add(page7);
        pages.add(page8);
        pages.add(page9);

        WikiReference ref09 = new WikiReference(page0.getPageId(), page0.getTitle(), page9.getPageId(), page9.getTitle());
        WikiReference ref08 = new WikiReference(page0.getPageId(), page0.getTitle(), page8.getPageId(), page8.getTitle());
        WikiReference ref07 = new WikiReference(page0.getPageId(), page0.getTitle(), page7.getPageId(), page7.getTitle());
        references.add(ref09);
        references.add(ref08);
        references.add(ref07);

        WikiReference ref85 = new WikiReference(page8.getPageId(), page8.getTitle(), page5.getPageId(), page5.getTitle());
        WikiReference ref84 = new WikiReference(page8.getPageId(), page8.getTitle(), page4.getPageId(), page4.getTitle());
        references.add(ref85);
        references.add(ref84);

        WikiReference ref54 = new WikiReference(page5.getPageId(), page5.getTitle(), page4.getPageId(), page4.getTitle());
        WikiReference ref53 = new WikiReference(page5.getPageId(), page5.getTitle(), page3.getPageId(), page3.getTitle());
        references.add(ref54);
        references.add(ref53);

        WikiReference ref31 = new WikiReference(page3.getPageId(), page3.getTitle(), page1.getPageId(), page1.getTitle());
        WikiReference ref30 = new WikiReference(page3.getPageId(), page3.getTitle(), page0.getPageId(), page0.getTitle());
        references.add(ref31);
        references.add(ref30);

        api = new WikiAssistApi(pages, references);
    }

    @Test
    public void testCycleDetection() {
        CycleDetector cd = new CycleDetector(api);
        final boolean found = cd.breadthFirstSearch("A");
        assertTrue(found == true);
    }

}
