package name.cphillipson.experimental.gwt.client.module.common.widget.grid;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.common.view.DisplayMode;
import name.cphillipson.experimental.gwt.client.module.main.Event;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.Payload;
import name.cphillipson.experimental.gwt.shared.bean.Operation;

public abstract class AbstractButtonBar {

    // keeps events ordered by {@link Operation#getOrder()}
    private Comparator<Event> comparator = new Comparator<Event>() {

        @Override
        public int compare(Event e1, Event e2) {
            int comparison = -1;
            if (e1 == e2 || e1.getOp().getOrder() == e2.getOp().getOrder()) {
                comparison = 0;
            } else if (e1.getOp().getOrder() > e2.getOp().getOrder()){
                comparison = 1;
            }
            return comparison;
        }

    };

    private Set<Event> viewModeEvents;
    private Set<Event> editModeEvents;
    private Set<Button> buttonsToDisplay;

    private Map<Operation, Button> allButtons;

    public AbstractButtonBar() {
        super();
        allButtons = new HashMap<Operation, Button>();
        viewModeEvents = new TreeSet<Event>(comparator);
        editModeEvents = new TreeSet<Event>(comparator);
        buttonsToDisplay = new LinkedHashSet<Button>();

        setDefaults();

        for (final Map.Entry<Operation, Button> entry: allButtons.entrySet()) {
            entry.getValue();
        }
    }

    protected abstract void setDefaults();

    public <E extends MainEventBus> void setInput(DisplayMode mode, Payload type, NavTarget target, E eventBus) {
        addEvents(Event.getEvents(type));
        if (mode.equals(DisplayMode.VIEW)) {
            prepareButtons(viewModeEvents, target, eventBus);
        } else if (mode.equals(DisplayMode.EDIT)) {
            prepareButtons(editModeEvents, target, eventBus);
        }
    }

    private <E extends MainEventBus> void prepareButtons(Set<Event> events, final NavTarget target, final E eventBus) {
        Button btn;
        for (final Event e: events) {
            btn = allButtons.get(e.getOp());
            if (btn != null) {
                btn.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        eventBus.dispatch(e.getEventName(), target.exOp());
                    }

                });
                buttonsToDisplay.add(btn);
            }
        }
    }

    private void addEvents(Set<Event> events) {
        for (final Event event: events) {
            addEvent(event);
        }
    }

    private void addEvent(Event event) {
        if (event.getDisplayMode().equals(DisplayMode.ALL)) {
            viewModeEvents.add(event);
            editModeEvents.add(event);
        } else if (event.getDisplayMode().equals(DisplayMode.VIEW)) {
            viewModeEvents.add(event);
        } else if (event.getDisplayMode().equals(DisplayMode.EDIT)) {
            editModeEvents.add(event);
        }
    }

    protected void addButton(Operation op, Button btn) {
        allButtons.put(op,  btn);
    }

    public Set<Button> getButtons() {
        return buttonsToDisplay;
    }
}
