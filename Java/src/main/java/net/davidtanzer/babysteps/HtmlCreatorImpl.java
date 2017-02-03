package net.davidtanzer.babysteps;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

public class HtmlCreatorImpl implements HtmlCreator {
    @Override
    public String createTimerHtml(String timerText, String bodyColor, boolean running) {
        String templateName = templateName(running);
        HashMap<String, Object> scope = scope(timerText, bodyColor);
        return timerHtml(templateName, scope);
    }

    private String timerHtml(String templateName, HashMap<String, Object> scope) {
        StringWriter writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(templateName);
        try {
            mustache.execute(writer, scope).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    private String templateName(boolean running) {
        if(running) {
            return "timerRunning.html";
        } else {
            return "timerNotRunning.html";
        }
    }

    private HashMap<String, Object> scope(String timerText, String bodyColor) {
        HashMap<String, Object> scope = new HashMap<>();
        scope.put("timerText", timerText);
        scope.put("bodyColor", bodyColor);
        return scope;
    }
}
