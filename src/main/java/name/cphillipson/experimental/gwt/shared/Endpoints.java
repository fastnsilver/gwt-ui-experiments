package name.cphillipson.experimental.gwt.shared;

/**
 * Service end points for this application.
 * 
 * @author cphillipson
 *
 */
public interface Endpoints {


    // PREFIXES

    public static final String SERVICES_PREFIX = "services";
    public static final String CLIENT_GWT_PREFIX = SERVICES_PREFIX + "/gwt";
    public static final String SERVER_GWT_PREFIX = "/**/" + SERVICES_PREFIX + "/gwt";
    public static final String CLIENT_REST_PREFIX = SERVICES_PREFIX + "/rest";
    public static final String SERVER_REST_PREFIX = "/**/" + SERVICES_PREFIX + "/rest";


    // REST

    public static final String GET_LOCATIONS = "/get/locations";
    public static final String GET_RESOURCES = "/get/resources";

    // GWT

    public static final String GET_HEADER = "/get/header";
    public static final String GET_NAVIGATION = "/get/navigation";
    public static final String GET_ENERGY_OFFERS = "/get/energyOffers";
    public static final String GET_RESERVE_OFFERS = "/get/reserveOffers";

}
