package name.cphillipson.experimental.gwt.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Selector;
import com.google.gwt.query.client.Selectors;

/**
 * GWTQuery <a href="http://code.google.com/p/gwtquery/wiki/GettingStarted#Compile_Time_Selectors">compile-time selectors</a>.
 * @author cphillipson
 *
 */
public interface UiSelectors extends Selectors {

    public static final UiSelectors INSTANCE = GWT.create(UiSelectors.class);

    @Selector(".tabrow")
    GQuery getTabRow();

    @Selector(".tabrow li")
    GQuery getTabs();

}
