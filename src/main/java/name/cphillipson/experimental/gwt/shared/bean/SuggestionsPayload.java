package name.cphillipson.experimental.gwt.shared.bean;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import name.cphillipson.experimental.gwt.client.module.common.widget.suggest.MultivalueSuggestBox;

/**
 * Payload for use with *RestController classes. The {@link MultivalueSuggestBox} component consumes this payload in JSON format.
 * @author cphillipson
 *
 */
public class SuggestionsPayload {

    @JsonProperty("TotalSize")
    @JsonSerialize @JsonDeserialize
    private int totalSize;
    @JsonProperty("Options")
    @JsonSerialize @JsonDeserialize
    private List<SuggestionOption> options;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<SuggestionOption> getOptions() {
        return options;
    }

    public void setOptions(List<SuggestionOption> options) {
        this.options = options;
    }

    public static class SuggestionOption {

        @JsonProperty("Value")
        @JsonSerialize @JsonDeserialize
        private String value;
        @JsonProperty("DisplayName")
        @JsonSerialize @JsonDeserialize
        private String displayName;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

    }
}
