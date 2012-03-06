package name.cphillipson.experimental.gwt.client.module.common.dto;

import java.io.Serializable;

public enum MarketStatusDTO implements Serializable {

    OPEN("open"), CLOSED("closed");

    private static final long serialVersionUID = 1L;
    private final String state;

    MarketStatusDTO(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}