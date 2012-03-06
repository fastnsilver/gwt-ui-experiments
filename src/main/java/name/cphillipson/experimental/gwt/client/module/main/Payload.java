package name.cphillipson.experimental.gwt.client.module.main;


/**
 * Corresponds to a type (or categorical type) from a web service schema.
 * Used by {@link name.cphillipson.experimental.gwt.client.module.main.Event} to build event name when combined with {@link Operation}.
 * @author cphillipson
 *
 */
public enum Payload {

    RESOURCE_OFFER("ResourceOffer"),
    ENERGY_OFFER("EnergyOffer"),
    RESERVE_OFFER("ReserveOffer");

    private String type;

    private Payload(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
