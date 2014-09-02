package com.vaadin.demo.mobilemail;

import com.vaadin.addon.touchkit.annotations.CacheManifestEnabled;
import com.vaadin.addon.touchkit.annotations.OfflineModeEnabled;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@OfflineModeEnabled(false)
@CacheManifestEnabled(false)
// Use default widgetset.
@Widgetset("com.vaadin.DefaultWidgetSet")
public class FallbackUI extends UI {

    private static final String MSG = "<h1>Ooops...</h1> You accessed this demo "
            + "with a browser that is currently not supported by TouchKit. "
            + "TouchKit is "
            + "ment to be used with modern webkit based mobile browsers, "
            + "e.g. with iPhone. Curretly those "
            + "cover huge majority of actively used mobile browsers. "
            + "Support will be extended as other mobile browsers develop "
            + "and gain popularity. Testing ought to work with desktop "
            + "Safari or Chrome as well.";

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.addComponent(new Label(MSG, ContentMode.HTML));
        setContent(verticalLayout);
    }

}
