package name.cphillipson.experimental.gwt.client.module.common.widget.grid;


import java.util.List;
import java.util.Set;

import name.cphillipson.experimental.gwt.client.module.common.view.DisplayMode;
import name.cphillipson.experimental.gwt.shared.bean.TableMetadata;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

/**
 * Base for dual display mode grid implementations based on GWT's <code>DataGrid</code>.
 * Concrete grids are instantiated in view mode and are responsible for rendering in either view or edit modes.
 * No paging support! Suitable for rendering tables of 250 cells or less.
 * 
 * @param <T> a data transfer object
 * @author cphillipson
 *
 */
public abstract class ToggleableGrid<T> extends DataGrid<T> {

    private static final DisplayMode DEFAULT_MODE = DisplayMode.VIEW;

    // default, use in UiBinder template when you just want to @UiField inject a subclass
    public ToggleableGrid() {
        super();
    }

    // alternate, use in UiBinder template when you want to create an instance yourself, be user to inject w/ @UiField(provided=true)
    public ToggleableGrid(int pageSize, Resources resources) {
        super(pageSize, resources);
    }

    protected void setDefaults() {
        // Set the message to display when the table is empty.
        setEmptyTableWidget(new Label(UiMessages.INSTANCE.no_results()));
        // Add a selection model so we can select cells
        final SelectionModel<T> selectionModel = new MultiSelectionModel<T>();
        setSelectionModel(selectionModel, DefaultSelectionEventManager.<T> createDefaultManager());
    }

    public void setInput(List<T> content) {
        setInput(content, DEFAULT_MODE);
    }

    public void setInput(List<T> content, DisplayMode mode) {
        setDefaults();
        resetTableColumns();
        final ListDataProvider<T> dataProvider = new ListDataProvider<T>(content);
        final ListHandler<T> sortHandler = new ListHandler<T>(dataProvider.getList());
        addColumnSortHandler(sortHandler);
        initializeStructure(constructMetadata(), sortHandler, mode);
        dataProvider.addDataDisplay(this);
    }

    // see http://stackoverflow.com/questions/3772480/remove-all-columns-from-a-celltable
    // concrete classes are forced to maintain a handle on all columns added
    private void resetTableColumns() {
        for (final Column<T, ?> column: allColumns()) {
            removeColumn(column);
        }
        allColumns().clear();
    }

    public boolean isInEditMode(DisplayMode currentDisplayMode) {
        boolean result = false;
        if (currentDisplayMode.equals(DisplayMode.EDIT)) {
            result = true;
        }
        return result;
    }

    protected abstract Set<Column<T, ?>> allColumns();

    protected abstract TableMetadata constructMetadata();

    protected abstract void initializeStructure(TableMetadata metadata, ListHandler<T> sortHandler, DisplayMode mode);

    protected void setColumnHorizontalAlignment(Column<T, ?> column, HorizontalAlignmentConstant alignment) {
        column.setHorizontalAlignment(alignment);
    }

    @Override
    public void addColumn(Column<T, ?> column, String columnHeaderName) {
        final SafeHtmlBuilder sb = new SafeHtmlBuilder();
        sb.appendHtmlConstant("<div align=\"right\">").appendEscaped(columnHeaderName).appendHtmlConstant("</div>");
        final SafeHtml header = sb.toSafeHtml();
        addColumn(column, header);
        allColumns().add(column);
    }

    protected CompositeCell<T> generateCompositeCell(final List<HasCell<T, ?>> hasCells) {
        final CompositeCell<T> compositeCell = new CompositeCell<T>(hasCells) {

            @Override
            public void render(Context context, T value, SafeHtmlBuilder sb) {
                sb.appendHtmlConstant("<table><tbody><tr>");
                super.render(context, value, sb);
                sb.appendHtmlConstant("</tr></tbody></table>");
            }

            @Override
            protected Element getContainerElement(Element parent) {
                // Return the first TR element in the table.
                return parent.getFirstChildElement().getFirstChildElement().getFirstChildElement();
            }

            @Override
            protected <X> void render(Context context, T value,
                    SafeHtmlBuilder sb, HasCell<T, X> hasCell) {
                final Cell<X> cell = hasCell.getCell();
                sb.appendHtmlConstant("<td>");
                cell.render(context, hasCell.getValue(value), sb);
                sb.appendHtmlConstant("</td>");
            }
        };
        return compositeCell;
    }

}