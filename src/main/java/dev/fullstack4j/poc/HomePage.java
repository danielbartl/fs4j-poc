package dev.fullstack4j.poc;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.eclipse.store.storage.types.StorageManager;

import java.util.Optional;

public class HomePage extends WebPage {

    private Label counter;
    private AjaxFallbackLink<Void> likeButton;
    private IModel<Integer> counterValue;

    @SpringBean
    StorageManager storageManager;

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        // Bootstrap 5 CSS
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"));
        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"));

        // Bootstrap Icons
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css"));

    }

    @Override
    protected void onInitialize() {

        super.onInitialize();
        //counter();
        //likeButton();

    }

    private void counter() {

        counterValue = LambdaModel.of(() -> getLikesStorage().getLikes());
        counter = new Label("counter", counterValue);
        counter.setOutputMarkupId(true);
        add(counter);

    }

    private void likeButton() {

        likeButton = new AjaxFallbackLink<>("likeButton") {

            @Override
            public void onClick(Optional<AjaxRequestTarget> target) {

                getLikesStorage().incrementLikes();
                storageManager.storeRoot();
                target.ifPresent(t -> t.add(counter));

            }

        };
        add(likeButton);

    }

    private LikesStorage getLikesStorage() {
        return (LikesStorage) storageManager.root();
    }
}
