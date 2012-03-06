package name.cphillipson.experimental.gwt.client.module.energy.dto;


public enum RampRateTypeType {

    UP,
    EMERUP,
    DN,
    EMERDN;

    public String value() {
        return name();
    }

    public static RampRateTypeType fromValue(String v) {
        return valueOf(v);
    }

}
