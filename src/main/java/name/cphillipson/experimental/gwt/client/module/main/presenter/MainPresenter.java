package name.cphillipson.experimental.gwt.client.module.main.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import name.cphillipson.experimental.gwt.client.module.common.widget.popup.ExceptionDialog;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.view.IMainView;
import name.cphillipson.experimental.gwt.client.module.main.view.IMainView.IMainPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.impl.MainView;

@Presenter(view = MainView.class)
public class MainPresenter extends BasePresenter<IMainView, MainEventBus> implements IMainPresenter {

    @Override
    public void onInit() {
        // TODO Here is where we need to transact a messages and/or notifications service(s)
        // Get messages for currently logged in user, and if there are any, show them
        // Current plan is to have messages appear in a pop-up dialog (perhaps with auto-hide?)
    }

    // FIXME Unfortunately we are missing vital detail here...
    // See http://cleancodematters.wordpress.com/2011/05/29/improved-exceptionhandling-with-gwts-requestfactory/
    // If we re-implement services (updating from RPC-style to RequestFactory), then we can get more info
    // for uncaught exceptions by implementing a custom servlet-handler pair
    @Override
    public void onHandle(Throwable caught) {
        final ExceptionDialog ed = new ExceptionDialog(caught.getMessage(), "");
        view.setContent(ed);
    }

    @Override
    public void onShow404() {
        final String message = "Oops! It looks as if you may have taken a wrong turn.  Don't worry... It happens to the best of us!  Try again.";
        view.setContent(new Label(message));
    }

    @Override
    public void onSetContent(IsWidget content) {
        view.setContent(content);
    }

}
