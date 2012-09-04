package com.vaadin.demo.mobilemail.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.addon.touchkit.ui.NavigationBar;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.demo.mobilemail.data.AbstractPojo;
import com.vaadin.demo.mobilemail.data.DummyDataUtil;
import com.vaadin.demo.mobilemail.data.Folder;
import com.vaadin.demo.mobilemail.data.Message;
import com.vaadin.demo.mobilemail.data.MessageStatus;
import com.vaadin.demo.mobilemail.data.MobileMailContainer;
import com.vaadin.demo.mobilemail.data.ParentMessageFilter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.themes.Reindeer;

public class MessageHierarchyView extends NavigationView implements
        LayoutClickListener, Button.ClickListener {

    private static final long serialVersionUID = 1L;

    private final MobileMailContainer ds = DummyDataUtil.getContainer();

    private boolean editMode = false;

    private Table table;
    private Button editBtn;

    private final Map<Message, CheckBox> messageSelectMap = new HashMap<Message, CheckBox>();

    private Button archiveButton;

    private Button moveButton;

    private Folder folder;

    /**
     * A message button which can be selected. Contains the sender, subject and
     * a shortened version of the body
     * 
     */
    private class MessageButton extends CssLayout {
        private static final long serialVersionUID = 1L;

        private final Message message;

        private static final String STYLENAME = "message-button";

        public MessageButton(Message message) {
            this.message = message;

            setWidth("100%");
            setStyleName(STYLENAME);

            String subject = message.getMessageField("subject").getValue();
            String body = message.getMessageField("body").getValue();
            String from = message.getMessageField("from").getValue();

            Label lbl = new Label(from);
            lbl.setStyleName(STYLENAME + "-from");
            lbl.setWidth("-1px");
            lbl.addStyleName(Reindeer.LABEL_H2);
            addComponent(lbl);

            lbl = new Label("Today"); // FIXME Should be date from message
            lbl.setWidth("-1px");
            lbl.setStyleName(STYLENAME + "-time");
            lbl.addStyleName(Reindeer.LABEL_SMALL);
            addComponent(lbl);

            Label header = new Label();
            header.setHeight("1.5em");
            header.setWidth("-1px");
            if (subject.length() > 35) {
                header.setValue(subject.substring(0, 35) + "...");
            } else {
                header.setValue(subject);
            }
            addComponent(header);

            Label content = new Label();
            if (body.length() > 80) {
                content.setValue(body.replaceAll("\\<.*?\\>", "").substring(0,
                        80)
                        + "...");
            } else {
                content.setValue(body.replaceAll("\\<.*?\\>", ""));
            }

            content.setStyleName(Reindeer.LABEL_SMALL);
            addComponent(content);
        }

        public Message getMessage() {
            return message;
        }
    }

    @SuppressWarnings("serial")
    public MessageHierarchyView(final NavigationManager nav, final Folder folder) {
        addStyleName("message-list");

        this.folder = folder;
        int newMessages = 0;
        for (AbstractPojo child : folder.getChildren()) {
            if (child instanceof Message) {
                Message msg = (Message) child;
                newMessages += msg.getStatus() == MessageStatus.NEW ? 1 : 0;
            }
        }

        if (newMessages > 0) {
            setCaption(folder.getName() + " (" + newMessages + ")");
        } else {
            setCaption(folder.getName());
        }

        editBtn = new Button("Edit");
        editBtn.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                if (editMode) {
                    editBtn.setCaption("Edit");
                    editBtn.removeStyleName("blue");
                    editMode = false;
                    table.setSelectable(true);
                    table.setVisibleColumns(new Object[] { "new", "name" });
                    setToolbar(MailboxHierarchyView.createToolbar());

                    table.setColumnExpandRatio("name", 1);

                } else {
                    for (CheckBox cb : messageSelectMap.values()) {
                        cb.setValue(false);
                    }

                    selected.clear();

                    editBtn.setCaption("Cancel");
                    editBtn.addStyleName("blue");
                    editMode = true;
                    table.select(null);
                    table.setSelectable(false);
                    table.setVisibleColumns(new Object[] { "selected", "new",
                            "name" });
                    setToolbar(createEditToolbar());

                    table.setColumnExpandRatio("name", 1);
                }

                // Enable disable message view
                setMessageViewEnabled(!editMode);

                // Hide the back button while editing
                getNavigationBar().getComponentIterator().next()
                        .setVisible(!editMode);
            }
        });

        setRightComponent(editBtn);

        // Filter the messages which belong to the selected folder
        ds.setFilter(new ParentMessageFilter(folder));

        table = new Table(null, ds);
        table.setImmediate(true);
        table.setSelectable(true);
        table.setMultiSelect(false);
        table.setNullSelectionAllowed(false);
        table.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
        table.setSizeFull();

        // Add a selected colum
        table.addGeneratedColumn("selected", new Table.ColumnGenerator() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component generateCell(Table source, final Object itemId,
                    Object columnId) {
                if (!messageSelectMap.containsKey(itemId)) {
                    final CheckBox cb = new CheckBox();
                    cb.setImmediate(true);
                    cb.setStyleName("selected-checkbox");
                    cb.addValueChangeListener(new Property.ValueChangeListener() {
                        @Override
                        public void valueChange(ValueChangeEvent event) {
                            if (cb.getValue()) {
                                selected.add((Message) itemId);
                            } else {
                                selected.remove(itemId);
                            }

                            if (selected.isEmpty()) {
                                moveButton.setCaption("Move");
                                archiveButton.setCaption("Archive");
                            } else {
                                moveButton.setCaption("Move ("
                                        + selected.size() + ")");
                                archiveButton.setCaption("Archive ("
                                        + selected.size() + ")");
                            }
                        }
                    });

                    messageSelectMap.put((Message) itemId, cb);
                }

                return messageSelectMap.get(itemId);
            }
        });
        table.setColumnWidth("selected", 30);

        // Add a new item column
        table.addGeneratedColumn("new", new Table.ColumnGenerator() {
            @Override
            public Component generateCell(Table source, Object itemId,
                    Object columnId) {
                Message msg = (Message) itemId;
                if (msg.getStatus() == MessageStatus.NEW) {
                    Label lbl = new Label("&nbsp;", ContentMode.XHTML);
                    lbl.setStyleName("new-marker");
                    lbl.setWidth("-1px");
                    return lbl;
                }
                return null;
            }
        });

        // Replace name column with navigation buttons
        table.addGeneratedColumn("name", new Table.ColumnGenerator() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component generateCell(Table source, Object itemId,
                    Object columnId) {
                final Message m = (Message) itemId;
                MessageButton btn = new MessageButton(m);
                btn.addLayoutClickListener(MessageHierarchyView.this);
                return btn;
            }
        });
        table.setColumnExpandRatio("name", 1);

        if (editMode) {
            table.setVisibleColumns(new Object[] { "selected", "new", "name" });
        } else {
            table.setVisibleColumns(new Object[] {  "new", "name" });
        }

        table.addItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                Message msg = (Message) event.getItemId();
                messageClicked(msg);
            }
        });

        setContent(table);
        setToolbar(MailboxHierarchyView.createToolbar());
    }

    List<Message> selected = new ArrayList<Message>();

    @Override
    public void layoutClick(LayoutClickEvent event) {
        MessageButton btn = (MessageButton) event.getSource();

        Message msg = btn.getMessage();

        messageClicked(msg);
    }

    private void messageClicked(Message msg) {
        if (editMode) {
            table.select(null);
            CheckBox cb = messageSelectMap.get(msg);
            cb.setValue(!cb.getValue());
        } else {
            table.select(msg);
            if (!editMode || !isSmartphone()) {
                setMessage(msg);
            }
        }
    }

    private void setMessage(final Message message) {
        ComponentContainer cc = getUI().getContent();
        if (cc instanceof MainView) {
            MainView mainView = (MainView) cc;
            mainView.setMessage(message, this);
        }
    }

    private void setMessageViewEnabled(boolean enabled) {
        if (!isSmartphone()) {
            ComponentContainer cc = getUI().getContent();
            TabletMainView tmv = (TabletMainView) cc;
            tmv.setEnabled(enabled);
        }

    }

    @Override
    protected void onBecomingVisible() {
        super.onBecomingVisible();
        if (!isSmartphone()) {
            if (ds.size() > 0 && table.getValue() == null) {
                table.select(ds.getIdByIndex(0));
                setMessage((Message) table.getValue());
            }
            setMessageViewEnabled(true);

            if (folder.getChildren().isEmpty()) {
                setMessage(null);
                editBtn.setEnabled(false);
            }
        }

    }

    private boolean isSmartphone() {
        return (getParent() instanceof SmartphoneMainView);
    }

    protected Component createEditToolbar() {
        final NavigationBar toolbar = new NavigationBar();

        archiveButton = new Button("Archive", this);
        archiveButton.setStyleName("red");
        toolbar.setLeftComponent(archiveButton);

        moveButton = new Button("Move", this);
        moveButton.setStyleName("blue");
        toolbar.setRightComponent(moveButton);

        return toolbar;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        Notification.show("Not implemented");

    }

    public void selectMessage(Message msg) {
        table.setValue(msg);
    }
}
