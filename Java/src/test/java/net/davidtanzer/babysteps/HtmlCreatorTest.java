package net.davidtanzer.babysteps;

import org.approvaltests.Approvals;
import org.junit.Test;

public class HtmlCreatorTest {
    private HtmlCreator htmlCreator = new HtmlCreatorImpl();

    @Test
    public void testTimerHtml() {
        String first = htmlCreator.createTimerHtml("first random text", "first body color", true);
        String second = htmlCreator.createTimerHtml("second random text", "second body color", false);
        Approvals.verify(first + "\n" + second);
    }
}
