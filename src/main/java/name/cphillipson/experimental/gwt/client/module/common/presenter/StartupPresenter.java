package name.cphillipson.experimental.gwt.client.module.common.presenter;


/**
 * A presenter which provides a hook for calling back to the event bus at application startup time.
 * @author cphillipson
 *
 */
public interface StartupPresenter {
    void onStart();
}
