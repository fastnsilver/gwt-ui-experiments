package name.cphillipson.experimental.gwt.client.module.common.widget.grid;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import name.cphillipson.experimental.gwt.shared.bean.ReferenceData;

public class ReferenceDataBackedSelectionCell extends AbstractInputCell<String, String> {

    interface Template extends SafeHtmlTemplates {
        @Template("<option value=\"{0}\">{1}</option>")
        SafeHtml deselected(String optionSubmitValue, String optionDisplayValue);

        @Template("<option value=\"{0}\" selected=\"selected\">{1}</option>")
        SafeHtml selected(String optionSubmitValue, String optionDisplayValue);
    }

    private static Template template;

    private HashMap<String, String> indexForOption = new HashMap<String, String>();

    private final Map<String, String> options;

    /**
     * Construct a new {@link ReferenceDataBackedSelectionCell} from {@link ReferenceData}.
     *
     * @param refData data used to create the options in the cell
     */
    public ReferenceDataBackedSelectionCell(ReferenceData refData) {
        super("change");
        if (template == null) {
            template = GWT.create(Template.class);
        }
        if (refData == null) {
            throw new IllegalArgumentException("Reference data for selection cell may not be null");
        }
        options = new HashMap<String, String>(refData.allData());
        for (final Map.Entry<String, String> option : options.entrySet()) {
            indexForOption.put(option.getKey(), option.getValue());
        }
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, String value,
            NativeEvent event, ValueUpdater<String> valueUpdater) {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);
        final String type = event.getType();
        if ("change".equals(type)) {
            final Object key = context.getKey();
            final SelectElement select = parent.getFirstChild().cast();
            final String newValue = options.get(select.getSelectedIndex());
            setViewData(key, newValue);
            finishEditing(parent, newValue, key, valueUpdater);
            if (valueUpdater != null) {
                valueUpdater.update(newValue);
            }
        }
    }

    @Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {
        final String selectedIndex = getSelectedIndex(value == null ? "" : value);
        sb.appendHtmlConstant("<select tabindex=\"-1\">");
        String optionSubmitValue, optionDisplayValue = null;
        for (final Map.Entry<String, String> option : options.entrySet()) {
            optionSubmitValue = option.getValue();
            optionDisplayValue = option.getKey();
            if (optionSubmitValue.equals(selectedIndex)) {
                sb.append(template.selected(optionSubmitValue, optionDisplayValue));
            } else {
                sb.append(template.deselected(optionSubmitValue, optionDisplayValue));
            }
        }
        sb.appendHtmlConstant("</select>");
    }

    private String getSelectedIndex(String value) {
        final String index = indexForOption.get(value);
        if (index == null) {
            return "";
        }
        return index;
    }
}
