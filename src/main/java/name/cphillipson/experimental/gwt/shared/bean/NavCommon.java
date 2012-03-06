package name.cphillipson.experimental.gwt.shared.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Encapsulates common state and behavior for <code>Navigable</code> implementations.
 * @author cphillipson
 *
 */
public abstract class NavCommon implements Navigable {

    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;
    protected String parent;
    private Set<String> children;
    protected String type;
    protected String targetName;
    protected String op;

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getParent() {
        return parent;
    }

    @Override
    public boolean hasParent() {
        boolean result = false;
        if (parent != null) {
            result = true;
        }
        return result;
    }

    @Override
    public Set<String> getChildren() {
        if (children == null) {
            children = new HashSet<String>();
        }
        return children;
    }

    @Override
    public void addChild(Navigable navigable) {
        navigable.setParent(this.getId());
        getChildren().add(navigable.getId());
    }

    @Override
    public boolean hasChildren() {
        boolean result = true;
        if (getChildren().isEmpty()) {
            result = false;
        }
        return result;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getTargetName() {
        if (targetName == null) {
            targetName = name;
        }
        return targetName;
    }

    @Override
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public String getOp() {
        return op;
    }
}
