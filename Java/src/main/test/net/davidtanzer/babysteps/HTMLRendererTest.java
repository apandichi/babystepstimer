package net.davidtanzer.babysteps;


import org.junit.Assert;
import org.junit.Test;

public class HTMLRendererTest {
    @Test
    public void testOutput() {

        HTMLRenderer htmlRenderer = new HTMLRenderer("adc","abc,",true);

        String reseult = htmlRenderer.invoke();
        Assert.assertEquals("<html><body style=\"border: 3px solid #555555; background: abc,; margin: 0; padding: 0;\"><h1 style=\"text-align: center; font-size: 30px; color: #333333;\">adc</h1><div style=\"text-align: center\"><a style=\"color: #555555;\" href=\"command://stop\">Stop</a> <a style=\"color: #555555;\" href=\"command://reset\">Reset</a> <a style=\"color: #555555;\" href=\"command://quit\">Quit</a> </div></body></html>", reseult);
    }

}