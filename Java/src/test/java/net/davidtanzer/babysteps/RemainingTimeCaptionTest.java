package net.davidtanzer.babysteps;

import org.junit.Assert;
import org.junit.Test;

public class RemainingTimeCaptionTest {
    private RemainingTimeCaption remainingTimeCaption = new RemainingTimeCaptionImpl();

    @Test
    public void when0SecondsElapsedCaptionShouldBe20Seconds() {
        long secondsInCycle = 20L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle);
        Assert.assertEquals(caption, "00:20");
    }

    @Test
    public void when1SecondsElapsedCaptionShouldBe19Seconds() {
        long secondsInCycle = 20L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(1L, secondsInCycle);
        Assert.assertEquals(caption, "00:19");
    }

    @Test
    public void when2SecondsElapsedCaptionShouldBe18Seconds() {
        long secondsInCycle = 20L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(2L, secondsInCycle);
        Assert.assertEquals(caption, "00:18");
    }

    @Test
    public void when0SecondsElapsedOutOf59SecondsCaptionShouldBe59Seconds() {
        long secondsInCycle = 59L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle);
        Assert.assertEquals(caption, "00:59");
    }

    @Test
    public void when0SecondsElapsedOutOf60SecondsCaptionShouldBe1Minute() {
        long secondsInCycle = 60L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(0L, secondsInCycle);
        Assert.assertEquals(caption, "01:00");
    }

    @Test
    public void when2SecondsElapsedOutOf55SecondsCaptionShouldBe53Seconds() {
        long secondsInCycle = 55L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(2L, secondsInCycle);
        Assert.assertEquals(caption, "00:53");
    }

    @Test
    public void when2SecondsElapsedOutOf65SecondsCaptionShouldBe1MinuteAnd3Seconds() {
        long secondsInCycle = 65L;
        String caption = remainingTimeCaption.getRemainingTimeCaption(2L, secondsInCycle);
        Assert.assertEquals(caption, "01:03");
    }
}
