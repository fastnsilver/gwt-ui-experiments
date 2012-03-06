package name.cphillipson.experimental.gwt.client.module.main;

import java.util.HashSet;
import java.util.Set;

import name.cphillipson.experimental.gwt.client.module.common.view.DisplayMode;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.AbstractButtonBar;
import name.cphillipson.experimental.gwt.shared.bean.Operation;

/**
 * A collection of events that correspond to the actions that are available to perform on a type within
 * a mode or modes.  Used e.g., to generate button bars of actions.  Provides helper method to generate
 * a set of events for a given payload (type).  An event's name is either the name of a method in
 * {@link MainEventBus} or the name attribute of an {@link com.mvp4g.client.annotation.Event} annotated
 * method in {@link MainEventBus}.
 * @see AbstractButtonBar
 * @author cphillipson
 *
 */
public enum Event {

    VIEW_RESOURCE_OFFER(Payload.RESOURCE_OFFER, Operation.VIEW, DisplayMode.ALL),

    VIEW_RESERVE_OFFER(Payload.RESERVE_OFFER, Operation.VIEW, DisplayMode.EDIT),
    EDIT_RESERVE_OFFER(Payload.RESERVE_OFFER, Operation.EDIT, DisplayMode.VIEW),
    COPY_RESERVE_OFFER(Payload.RESERVE_OFFER, Operation.COPY, DisplayMode.ALL),
    DELETE_RESERVE_OFFER(Payload.RESERVE_OFFER, Operation.DELETE, DisplayMode.ALL),
    DOWNLOAD_RESERVE_OFFER(Payload.RESERVE_OFFER, Operation.DOWNLOAD, DisplayMode.ALL),
    SAVE_RESERVE_OFFER(Payload.RESERVE_OFFER, Operation.SAVE, DisplayMode.EDIT),
    RESET_RESERVE_OFFER(Payload.RESERVE_OFFER, Operation.RESET, DisplayMode.EDIT),

    VIEW_ENERGY_OFFER(Payload.ENERGY_OFFER, Operation.VIEW, DisplayMode.EDIT),
    EDIT_ENERGY_OFFER(Payload.ENERGY_OFFER, Operation.EDIT, DisplayMode.VIEW),
    COPY_ENERGY_OFFER(Payload.ENERGY_OFFER, Operation.COPY, DisplayMode.ALL),
    DELETE_ENERGY_OFFER(Payload.ENERGY_OFFER, Operation.DELETE, DisplayMode.ALL),
    DOWNLOAD_ENERGY_OFFER(Payload.ENERGY_OFFER, Operation.DOWNLOAD, DisplayMode.ALL),
    SAVE_ENERGY_OFFER(Payload.ENERGY_OFFER, Operation.SAVE, DisplayMode.EDIT),
    RESET_ENERGY_OFFER(Payload.ENERGY_OFFER, Operation.RESET, DisplayMode.EDIT);

    private Payload payload;
    private String eventName;
    private Operation op;
    private DisplayMode mode;

    private Event(Payload payload, Operation op, DisplayMode mode) {
        this.payload = payload;
        eventName = op.getCode() + payload.getType();
        this.op = op;
        this.mode = mode;
    }

    private Payload getPayload() {
        return payload;
    }

    public String getEventName() {
        return eventName;
    }

    public Operation getOp() {
        return op;
    }

    public DisplayMode getDisplayMode() {
        return mode;
    }

    public static Set<Event> getEvents(Payload payload) {
        final Set<Event> events = new HashSet<Event>();
        for (final Event e: values()) {
            if (payload.equals(e.getPayload())) {
                events.add(e);
            }
        }
        return events;
    }

}
