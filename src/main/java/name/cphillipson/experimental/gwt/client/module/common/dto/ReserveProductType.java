package name.cphillipson.experimental.gwt.client.module.common.dto;


public enum ReserveProductType {


    /**
     * 
     * 						Resource capacity held in reserve for the purpose of providing Regulation Deployment between zero Regulation Deployment and the up direction.
     * 
     * 
     */
    REGUP,

    /**
     * 
     * 						Resource capacity that is available for the purpose of providing Regulation Deployment between zero Regulation Deployment and the down direction.
     * 
     * 
     */
    REGDN,

    /**
     * 
     * 						The portion of Contingency Reserve consisting of Resources
     * 						synchronized to the system and fully available to serve load
     * 						within the Contingency Reserve Deployment Period following a
     * 						contingency event.
     * 
     * 
     */
    SPIN,

    /**
     * 
     * 						The portion of Operating Reserve consisting of
     * 						on-line Resources
     * 						and/or off-line Resources capable of being
     * 						synchronized to the
     * 						system that is fully available to serve load
     * 						within the
     * 						Contingency Reserve Deployment Period following a
     * 						contingency
     * 						event.
     * 
     * 
     */
    SUPP;

    public String value() {
        return name();
    }

    public static ReserveProductType fromValue(String v) {
        return valueOf(v);
    }

}
