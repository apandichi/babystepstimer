package net.davidtanzer.babysteps;

import org.junit.Assert;
import org.junit.Test;

public class RemainingTimeCaptionTest {
    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();

    @Test
    public void testRemainingTimeCaptionZero() {
        String caption = remainingTimeCaption.getRemainingTimeCaption(0L);
        Assert.assertEquals(caption, "00:20");
    }

    @Test
    public void testRemainingTimeCaptionTen() {
        String caption = remainingTimeCaption.getRemainingTimeCaption(10L);
        Assert.assertEquals(caption, "00:20");
    }

    @Test
    public void testRemainingTimeCaptionOneThousand() {
        String caption = remainingTimeCaption.getRemainingTimeCaption(1000L);
        Assert.assertEquals(caption, "00:19");
    }

    @Test
    public void testRemainingTimeCaptionTwoThousand() {
        String caption = remainingTimeCaption.getRemainingTimeCaption(2000L);
        Assert.assertEquals(caption, "00:18");
    }
}
