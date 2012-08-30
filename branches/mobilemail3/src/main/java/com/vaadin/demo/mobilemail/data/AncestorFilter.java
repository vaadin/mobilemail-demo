package com.vaadin.demo.mobilemail.data;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;

/**
 * A filter which lists all sublevels as well in the folder hierarchy
 * 
 * @author john
 * 
 */
public class AncestorFilter implements Filter {

    private static final long serialVersionUID = 1L;

    private AbstractPojo parent;

    public AncestorFilter(AbstractPojo parent) {
        this.parent = parent;
    }

    public boolean passesFilter(Object itemId, Item item)
            throws UnsupportedOperationException {
        AbstractPojo p = (AbstractPojo) itemId;
        if (p instanceof Folder) {
            AbstractPojo pparent = p;
            while (pparent != null) {
                pparent = pparent.getParent();
                if (pparent == null && parent == null) {
                    return true;
                } else if (pparent != null && pparent.equals(parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean appliesToProperty(Object propertyId) {
        return propertyId.equals("name");
    }
}
