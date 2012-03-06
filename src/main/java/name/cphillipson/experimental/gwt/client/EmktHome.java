package name.cphillipson.experimental.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;
import name.cphillipson.experimental.gwt.client.module.main.MainModule;
import name.cphillipson.experimental.gwt.client.resources.UiResources;


/**
 * The entry point for this GWT-based application.
 * @author cphillipson
 *
 */
public class EmktHome implements EntryPoint {

    @Override
    public void onModuleLoad() {
        final Mvp4gModule mvpModule = (Mvp4gModule) GWT.create(MainModule.class);
        mvpModule.createAndStartModule();
        RootLayoutPanel.get().add((Widget) mvpModule.getStartView());

        // http://stackoverflow.com/questions/2432232/gwt-uibinder-doesnt-load-the-stylesheet
        UiResources.INSTANCE.style().ensureInjected();
    }

}

