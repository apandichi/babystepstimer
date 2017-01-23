package net.davidtanzer.babysteps;

import org.junit.Assert;
import org.junit.Test;

public class RemainingTimeCaptionTest {
    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();

    @Test
    public void when0MillisecondsElapsedCaptionShouldBe20Seconds() {
        long secondsInCycle = 20L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle);
        Assert.assertEquals(caption, "00:20");
    }

    @Test
    public void when10MillisecondsElapsedCaptionShouldBe20Seconds() {
        long secondsInCycle = 20L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(10L, secondsInCycle);
        Assert.assertEquals(caption, "00:20");
    }

    @Test
    public void when1000MillisecondsElapsedCaptionShouldBe19Seconds() {
        long secondsInCycle = 20L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(1000L, secondsInCycle);
        Assert.assertEquals(caption, "00:19");
    }

    @Test
    public void when2000MillisecondsElapsedCaptionShouldBe18Seconds() {
        long secondsInCycle = 20L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(2000L, secondsInCycle);
        Assert.assertEquals(caption, "00:18");
    }
}
