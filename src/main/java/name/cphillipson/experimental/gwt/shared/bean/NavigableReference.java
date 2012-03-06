package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;

import name.cphillipson.experimental.gwt.client.util.ClientSafeUUIDGenerator;

/**
 * A reference to a "commonly used" <code>Navigable</code>.
 * E.g., where multiple instances of <code>NavOption</code> exist and each option's fields: <code>name</code>, <code>type</code>, <code>targetName</code> and <code>op</code> fields
 * are the same.  Useful when you want to serialize a reference to one instance rather than multiple instances.
 * 
 * @see NavigationInfo
 * 
 * @author cphillipson
 *
 */
public class NavigableReference implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String type;
    private String targetName;
    private String op;

    public NavigableReference() {
        // because GWT's serialization rules say I must have a zero-argument constructor
    }

    public NavigableReference(Navigable navigable) {
        id = ClientSafeUUIDGenerator.uuid();
        name = navigable.getName();
        type = navigable.getType();
        targetName = navigable.getTargetName();
        op = navigable.getOp();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getOp() {
        return op;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (op == null ? 0 : op.hashCode());
        result = prime * result + (targetName == null ? 0 : targetName.hashCode());
        result = prime * result + (type == null ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NavigableReference other = (NavigableReference) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (op == null) {
            if (other.op != null) {
                return false;
            }
        } else if (!op.equals(other.op)) {
            return false;
        }
        if (targetName == null) {
            if (other.targetName != null) {
                return false;
            }
        } else if (!targetName.equals(other.targetName)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }


}
