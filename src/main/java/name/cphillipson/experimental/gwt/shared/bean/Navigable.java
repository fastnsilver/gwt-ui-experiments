package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * Implementations encapsulate state and rules for hyperlink based navigation.
 * @author cphillipson
 *
 */
public interface Navigable extends Serializable {

    public static final String ID_KEY = "id";
    public static final String OP_KEY = "op";
    public static final String TYPE_KEY = "type";

    void setId(String id);
    String getId();
    void setParent(String id);
    String getParent();
    boolean hasParent();
    Set<String> getChildren();
    boolean hasChildren();
    void addChild(Navigable navigable);
    String getName();
    String getType();
    void setTargetName(String targetName);
    String getTargetName();
    String getOp();
    void setOp(String op);
}
