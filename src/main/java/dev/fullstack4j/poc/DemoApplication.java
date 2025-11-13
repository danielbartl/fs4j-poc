package dev.fullstack4j.poc;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.eclipse.store.storage.types.StorageManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    WebApplication wicketApp(ApplicationContext ctx, StorageManager storageManager) {

        return new WebApplication() {

            @Override
            public Class<? extends Page> getHomePage() {

                return HomePage.class;

            }

            @Override
            protected void init() {

                super.init();
                getComponentInstantiationListeners().add(
                        new SpringComponentInjector(this, ctx));
                getCspSettings().blocking().disabled();

                LikesStorage root = (LikesStorage) storageManager.root();

                if (root == null) {
                    root = new LikesStorage();
                    storageManager.setRoot(root);
                    storageManager.storeRoot();
                }

            }
        };

    }

    @Bean
    WicketFilter wicketFilter(WebApplication wicketApp) {

        final WicketFilter filter = new WicketFilter(wicketApp);
        filter.setFilterPath("/");
        return filter;

    }
}
