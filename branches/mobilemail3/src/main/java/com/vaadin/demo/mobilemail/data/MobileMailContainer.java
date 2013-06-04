package com.vaadin.demo.mobilemail.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

public class MobileMailContainer extends BeanItemContainer<AbstractPojo>
        implements Container.Hierarchical {

    private static final long serialVersionUID = 1L;

    public MobileMailContainer() {
        super(AbstractPojo.class);
    }

    @Override
    public Collection<? extends AbstractPojo> getChildren(Object parent) {
        List<AbstractPojo> children = new ArrayList<AbstractPojo>();
        for (AbstractPojo pojo : getAllItemIds()) {
            if (pojo.getParent() == parent) {
                children.add(pojo);
            }
        }
        return children;
    }

    @Override
    public Object getParent(Object itemId) {
        AbstractPojo pojo = (AbstractPojo) itemId;
        return pojo.getParent();
    }

    /**
     * Root items are Mailboxes so this returns the mailboxes
     */
    @Override
    public Collection<?> rootItemIds() {
        List<AbstractPojo> pojos = getAllItemIds();
        if (pojos != null) {
            List<MailBox> mailboxes = new ArrayList<MailBox>();
            for (AbstractPojo pojo : pojos) {
                if (isRoot(pojo)) {
                    mailboxes.add((MailBox) pojo);
                }
            }
            return mailboxes;
        }
        return null;
    }

    @Override
    public boolean setParent(Object itemId, Object newParentId)
            throws UnsupportedOperationException {
        if (itemId instanceof MailBox) {
            throw new UnsupportedOperationException(
                    "Mailboxes cannot have parents");
        } else if (itemId instanceof Message && newParentId instanceof MailBox) {
            throw new UnsupportedOperationException(
                    "Messages cannot be added to mailboxes");
        } else if (areChildrenAllowed(newParentId)) {
            AbstractPojo pojo = (AbstractPojo) itemId;
            pojo.setParent((AbstractPojo) newParentId);
            return true;
        }
        return false;
    }

    @Override
    public boolean areChildrenAllowed(Object itemId) {
        return itemId instanceof MailBox || itemId instanceof Folder;
    }

    @Override
    public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not in use");
    }

    @Override
    public boolean isRoot(Object itemId) {
        return itemId instanceof MailBox;
    }

    @Override
    public boolean hasChildren(Object itemId) {
        if (itemId instanceof Message) {
            return false;
        }
        for (AbstractPojo pojo : getAllItemIds()) {
            if (pojo.getParent() == itemId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BeanItem<AbstractPojo> addItem(Object itemId) {
        BeanItem<AbstractPojo> result = super.addItem(itemId);
        fireItemSetChange();
        return result;
    }

    public void setFilter(Filter filter) {
        removeAllContainerFilters();
        if (filter != null) {
            addFilter(filter);
        }
    }
}
