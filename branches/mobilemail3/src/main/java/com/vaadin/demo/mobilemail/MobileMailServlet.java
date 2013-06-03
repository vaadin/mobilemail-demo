package com.vaadin.demo.mobilemail;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class MobileMailServlet extends TouchKitServlet {

    private final UIProvider uiProvider = new UIProvider() {

        @Override
        public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
            String userAgent = event.getRequest().getHeader("user-agent")
                    .toLowerCase();
            if (userAgent.contains("webkit")) {
                return MobileMailUI.class;
            } else {
                return FallbackUI.class;
            }
        }
    };

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();

        getService().addSessionInitListener(new SessionInitListener() {
            @Override
            public void sessionInit(SessionInitEvent event)
                    throws ServiceException {
                event.getSession().addUIProvider(uiProvider);
            }
        });

    }

    @Override
    public URL getApplicationUrl(HttpServletRequest request)
            throws MalformedURLException {
        return super.getApplicationUrl(request);
    }
}
