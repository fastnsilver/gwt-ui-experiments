
package name.cphillipson.experimental.gwt.client.module.common.dto;


public enum ProductType {

    ENERGY,
    REGUP,
    REGDN,
    SPIN,
    SUPP;

    public String value() {
        return name();
    }

    public static ProductType fromValue(String v) {
        return valueOf(v);
    }

}
