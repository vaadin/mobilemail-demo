package com.vaadin.demo.mobilemail.data;

import com.vaadin.data.Item;

public class ParentMessageFilter extends ParentFilter {

    private static final long serialVersionUID = 1L;

    public ParentMessageFilter(AbstractPojo parent) {
        super(parent);
    }

    @Override
    public boolean passesFilter(Object itemId, Item item)
            throws UnsupportedOperationException {
        if (super.passesFilter(itemId, item)) {
            // Only messages should pass this filter
            return itemId instanceof Message;
        }
        return false;
    }

}
