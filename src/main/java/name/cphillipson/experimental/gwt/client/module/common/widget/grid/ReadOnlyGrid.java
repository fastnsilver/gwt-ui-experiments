package name.cphillipson.experimental.gwt.client.module.common.widget.grid;

import com.google.gwt.user.client.ui.FlexTable;
import name.cphillipson.experimental.gwt.client.resources.UiResources;
import name.cphillipson.experimental.gwt.shared.bean.DataTable;

/**
 * Relatively light-weight view component for table display. Receives input in a generic row-column form, and renders a simple read-only grid.
 * Based off GWT's FlexTable implementation. Lars Vogel's <a href="http://www.vogella.de/articles/GWT/article.html#server_table">GWT Tutorial</a>
 * was the main source of inspiration.
 * 
 * @author cphillipson
 */
public class ReadOnlyGrid extends FlexTable {

    private boolean shadeEvenRows, shadeOddRows, headerShown;
    private String headerStyleName;

    public ReadOnlyGrid() {
        super();
        setDefaults();
    }

    private void setDefaults() {
        setCellPadding(1);
        setCellSpacing(1);
        setWidth("100%");
    }

    public void setInput(DataTable input) {
        for (int i = this.getRowCount(); i > 0; i--) {
            this.removeRow(0);
        }
        if (input == null) {
            return;
        }

        int row = 0;
        if (isHeaderShown()) {
            final String[] header = input.getHeader();
            if (header != null) {
                int i = 0;
                for (final String string : header) {
                    this.setText(row, i, string);
                    i++;
                }
                row++;
            }
        }

        final int totalColumns = input.getColumnCount();
        final int totalRows = input.getRowCount();

        for (int c = 0; c < totalColumns; c++) {
            for (int r = 0; r < totalRows; r++) {
                final int currentRowIndex = r + row;
                this.setText(currentRowIndex, c, input.get(r, c));
                if (isShadeEvenRows() && currentRowIndex % 2 == 0) {
                    shadeRow(currentRowIndex);
                }
                if (isShadeOddRows() && currentRowIndex % 2 != 0) {
                    shadeRow(currentRowIndex);
                }
                rightAlignRow(currentRowIndex);
            }
        }
    }

    private void shadeRow(int row) {
        this.getRowFormatter().addStyleName(row, UiResources.INSTANCE.style().shadedRow());
    }

    private void rightAlignRow(int row) {
        this.getRowFormatter().addStyleName(row, UiResources.INSTANCE.style().rightAligned());
    }

    public boolean isShadeEvenRows() {
        return shadeEvenRows;
    }

    public void setShadeEvenRows(boolean shadeEvenRows) {
        this.shadeEvenRows = shadeEvenRows;
    }

    public boolean isShadeOddRows() {
        return shadeOddRows;
    }

    public void setShadeOddRows(boolean shadeOddRows) {
        this.shadeOddRows = shadeOddRows;
    }

    public boolean isHeaderShown() {
        return headerShown;
    }

    public void setHeaderShown(boolean headerShown) {
        this.headerShown = headerShown;
    }

    public void setHeaderStyleName(String styleName) {
        // no sense in setting header style if we're not going to display header
        if (!isHeaderShown()) {
            setHeaderShown(true);
        }
        if (styleName != null && !styleName.isEmpty()) {
            this.getRowFormatter().addStyleName(0, styleName);
        }
    }
}
