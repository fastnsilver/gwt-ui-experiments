package name.cphillipson.experimental.gwt.client.module.main;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import name.cphillipson.experimental.gwt.client.module.common.widget.popup.BusyIndicator;

/**
 * A convenience class used as param of an RPC service call.
 * @author cphillipson
 *
 * @param <DTO> a data transfer object, i.e., the result of the callback
 * @param <U> a view interface
 */
public abstract class SafeCallback<DTO, U extends IsWidget> implements AsyncCallback<DTO> {

    private final MainEventBus eventBus;
    private final U uiObject;
    final BusyIndicator indicator;

    public SafeCallback(U uiObject, MainEventBus eventBus) {
        this.uiObject = uiObject;
        this.eventBus = eventBus;
        indicator = new BusyIndicator();
        indicator.show();
    }

    @Override
    public void onFailure(Throwable caught) {
        if (caught instanceof ConstraintViolationException) {
            final ConstraintViolationException violationException = (ConstraintViolationException) caught;
            final Set<ConstraintViolation<?>> violations = violationException.getConstraintViolations();
            for (final ConstraintViolation<?> constraintViolation : violations) {
                // TODO build payload to send to MessagesView; perhaps this loop is performed in view and payload contains the violations?
            }
            // TODO call a variant of eventBus.populateMessages() and make sure presenter onPopulateMessages is updated to handle
        } else {
            eventBus.handle(caught);
        }
        indicator.hide();
    }

    @Override
    public void onSuccess(DTO dto) {
        onSuccessImpl(dto);
        indicator.hide();
    }

    public abstract void onSuccessImpl(DTO dto);

}
