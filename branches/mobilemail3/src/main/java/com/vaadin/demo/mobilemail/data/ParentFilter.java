package com.vaadin.demo.mobilemail.data;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;

/**
 * A filter for filtering out items with a certain parent. Item ids must be
 * beans for this to work
 */
public class ParentFilter implements Filter {

    private static final long serialVersionUID = 1L;

    private AbstractPojo parent;

    public ParentFilter(AbstractPojo parent) {
        this.parent = parent;
    }

    public boolean passesFilter(Object itemId, Item item)
            throws UnsupportedOperationException {
        AbstractPojo p = (AbstractPojo) itemId;
        if (p.getParent() == null) {
            return parent == null;
        }
        return p.getParent().equals(parent);
    }

    public boolean appliesToProperty(Object propertyId) {
        return propertyId.equals("name");
    }
}
