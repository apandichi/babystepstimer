package net.davidtanzer.babysteps;

import org.junit.Assert;
import org.junit.Test;

public class RemainingTimeCaptionTest {
    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();

    @Test
    public void when0MillisecondsElapsedCaptionShouldBe20Seconds() {
        String caption = remainingTimeCaption.getRemainingTimeCaption(0L);
        Assert.assertEquals(caption, "00:20");
    }

    @Test
    public void when10MillisecondsElapsedCaptionShouldBe20Seconds() {
        String caption = remainingTimeCaption.getRemainingTimeCaption(10L);
        Assert.assertEquals(caption, "00:20");
    }

    @Test
    public void when1000MillisecondsElapsedCaptionShouldBe19Seconds() {
        String caption = remainingTimeCaption.getRemainingTimeCaption(1000L);
        Assert.assertEquals(caption, "00:19");
    }

    @Test
    public void when2000MillisecondsElapsedCaptionShouldBe18Seconds() {
        String caption = remainingTimeCaption.getRemainingTimeCaption(2000L);
        Assert.assertEquals(caption, "00:18");
    }
}
