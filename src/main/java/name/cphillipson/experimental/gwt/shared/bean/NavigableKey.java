package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;

/**
 * A key used for mapping a <code>Navigable</code> instance to {@link NavigableReference#getId()}.
 * @author cphillipson
 *
 */
public class NavigableKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String parent;

    public NavigableKey() {
        // because GWT's serialization rules say I must have a zero-argument constructor
    }

    public NavigableKey(Navigable navigable) {
        id = navigable.getId();
        parent = navigable.getParent();
    }

    public String getId() {
        return id;
    }

    public String getParent() {
        return parent;
    }
}
