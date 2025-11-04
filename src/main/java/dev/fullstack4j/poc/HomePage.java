package dev.fullstack4j.poc;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/daisyui@5"));

    }
}
