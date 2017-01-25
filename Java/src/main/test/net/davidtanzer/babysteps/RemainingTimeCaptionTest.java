package net.davidtanzer.babysteps;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alin on 25.01.2017.
 */
public class RemainingTimeCaptionTest {

    @Test
    public void  after1msOutOf6sIs_00_06() {
        BabystepsTimer babystepsTimer = new BabystepsTimer(6);
        String timeCaption = babystepsTimer.getRemainingTimeCaption(1L);

        Assert.assertEquals("00:06", timeCaption);
    }
    @Test
    public void  after2sOutOf4sIs_00_02(){
        BabystepsTimer babystepsTimer = new BabystepsTimer(4);
        String timeCaption = babystepsTimer.getRemainingTimeCaption(2000L);

        Assert.assertEquals("00:02", timeCaption);
    }
}
