package net.davidtanzer.babysteps;

/**
 * Created by alin on 25.01.2017.
 */
class HTMLRenderer {
    private String timerText;
    private String bodyColor;
    private boolean running;

    public HTMLRenderer(String timerText, String bodyColor, boolean running) {
        this.timerText = timerText;
        this.bodyColor = bodyColor;
        this.running = running;
    }

    public String invoke() {
        String timerHtml = "<html><body style=\"border: 3px solid #555555; background: " + bodyColor + "; margin: 0; padding: 0;\">" +
                "<h1 style=\"text-align: center; font-size: 30px; color: #333333;\">" + timerText + "</h1>" +
                "<div style=\"text-align: center\">";
        if (running) {
            timerHtml += "<a style=\"color: #555555;\" href=\"command://stop\">Stop</a> " +
                    "<a style=\"color: #555555;\" href=\"command://reset\">Reset</a> ";
        } else {
            timerHtml += "<a style=\"color: #555555;\" href=\"command://start\">Start</a> ";
        }
        timerHtml += "<a style=\"color: #555555;\" href=\"command://quit\">Quit</a> ";
        timerHtml += "</div>" +
                "</body></html>";
        return timerHtml;
    }
}
