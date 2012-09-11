package com.vaadin.demo.mobilemail.ui;

import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.addon.touchkit.ui.NavigationButton.NavigationButtonClickEvent;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.demo.mobilemail.data.AbstractPojo;
import com.vaadin.demo.mobilemail.data.AncestorFilter;
import com.vaadin.demo.mobilemail.data.DummyDataUtil;
import com.vaadin.demo.mobilemail.data.Folder;
import com.vaadin.demo.mobilemail.data.MailBox;
import com.vaadin.demo.mobilemail.data.Message;
import com.vaadin.demo.mobilemail.data.MessageStatus;
import com.vaadin.demo.mobilemail.data.MobileMailContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;

public class FolderHierarchyView extends NavigationView {

    private static final long serialVersionUID = 1L;

    private final MobileMailContainer ds = DummyDataUtil.getContainer();

    private final Resource parentFolderIcon = new ThemeResource(
            "../runo/icons/64/folder.png");

    private final Resource childFolderIcon = new ThemeResource(
            "../runo/icons/64/folder.png");

    private final Resource trashIcon = new ThemeResource(
            "../runo/icons/64/trash.png");

    private final Resource sentIcon = new ThemeResource(
            "../runo/icons/64/email-send.png");

    private final Resource draftIcon = new ThemeResource(
            "../runo/icons/64/document-edit.png");

    public FolderHierarchyView(final NavigationManager nav, final MailBox mb) {

        if (mb.getName().length() > 10) {
            setCaption(mb.getName().substring(0, 10) + "...");
        } else {
            setCaption(mb.getName());
        }

        setWidth("100%");
        setHeight("100%");

        ds.setFilter(new AncestorFilter(mb));

        Table table = new Table(null, ds);
        table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        table.setVisibleColumns(new Object[] { "name" });
        table.setSizeFull();

        table.addGeneratedColumn("name", new Table.ColumnGenerator() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component generateCell(Table source, Object itemId,
                    Object columnId) {
                if (columnId.equals("name") && itemId instanceof Folder) {
                    final Folder f = (Folder) itemId;

                    // Resolve folder level
                    int level = 0;
                    AbstractPojo parent = f.getParent();
                    while (!(parent instanceof MailBox)) {
                        level++;
                        parent = parent.getParent();
                    }

                    NavigationButton btn = new NavigationButton(f.getName());

                    // Set new messages
                    int newMessages = 0;
                    for (AbstractPojo child : f.getChildren()) {
                        if (child instanceof Message) {
                            Message msg = (Message) child;
                            newMessages += msg.getStatus() == MessageStatus.NEW ? 1
                                    : 0;
                        }
                    }
                    if (newMessages > 0) {
                        btn.setDescription(newMessages + "");
                    }
                    btn.addStyleName("pill");
                    btn.addClickListener(new NavigationButton.NavigationButtonClickListener() {

                        private static final long serialVersionUID = 1L;

                        @Override
                        public void buttonClick(NavigationButtonClickEvent event) {
                            nav.navigateTo(new MessageHierarchyView(nav, f));
                        }
                    });

                    if (f.getParent() instanceof MailBox) {
                        btn.setIcon(parentFolderIcon);

                        if (f.getName().equals("Trash")) {
                            btn.setIcon(trashIcon);
                        } else if (f.getName().equals("Sent Mail")) {
                            btn.setIcon(sentIcon);
                        } else if (f.getName().equals("Drafts")) {
                            btn.setIcon(draftIcon);
                        }
                    } else {
                        btn.setIcon(childFolderIcon);
                    }

                    if (level == 0) {
                        return btn;
                    } else {
                        CssLayout layout = new CssLayout();
                        layout.addStyleName("indent-layout-level" + level);
                        layout.addComponent(btn);
                        return layout;
                    }
                }
                return null;
            }
        });

        setContent(table);
        setToolbar(MailboxHierarchyView.createToolbar());
    }

}
