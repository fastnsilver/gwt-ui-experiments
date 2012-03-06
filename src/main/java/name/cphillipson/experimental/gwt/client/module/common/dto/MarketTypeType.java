package name.cphillipson.experimental.gwt.client.module.common.dto;


public enum MarketTypeType {

    DAMKT,
    RTBM;

    public String value() {
        return name();
    }

    public static MarketTypeType fromValue(String v) {
        return valueOf(v);
    }

}
