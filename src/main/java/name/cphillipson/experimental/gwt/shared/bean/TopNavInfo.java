package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * (TopNav) payload for <code>HeaderService</code>.
 * @cphillipson
 */
public class TopNavInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Set<NavOption> options = new LinkedHashSet<NavOption>();

    public Set<NavOption> getOptions() {
        return options;
    }

    public void setOptions(Set<NavOption> options) {
        this.options = options;
    }

    public void addOption(NavOption option) {
        options.add(option);
    }
}
