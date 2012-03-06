package name.cphillipson.experimental.gwt.client.module.common.service;

import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;


/**
 * A builder interface for hierarchical navigation structures.
 * @author cphillipson
 *
 * @param <H> a hierarchical view structure (e.g., GWT Tree, CellTree)
 */
public interface NavBuilder<H> {

    H build(NavigationInfo info);
}
