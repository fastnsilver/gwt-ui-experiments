package name.cphillipson.experimental.gwt.client.module.reserve.widget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.cphillipson.experimental.gwt.client.module.common.view.DisplayMode;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.AbstractValidatableColumn;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.ReferenceDataBackedSelectionCell;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.ToggleableGrid;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferDTO;
import name.cphillipson.experimental.gwt.client.util.TimeUtil;
import name.cphillipson.experimental.gwt.shared.bean.ReferenceData;
import name.cphillipson.experimental.gwt.shared.bean.TableMetadata;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.IdentityColumn;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class ReserveOfferGrid extends ToggleableGrid<ReserveOfferDTO> {

    public ReserveOfferGrid() {
        super();
    }

    public ReserveOfferGrid(int pageSize, Resources resources) {
        super(pageSize, resources);
    }

    // XXX Should the "get*" service that prepares the data be responsible for generating empty:
    //     (a) rows for hours not persisted in dB?
    // XXX Should the "post*" service that submits, prune empty rows before posting to dB?

    private static final int MAX_NUMBER_OF_MW_PRICE_POINTS = 1;

    private Set<Column<ReserveOfferDTO, ?>> columns = new HashSet<Column<ReserveOfferDTO, ?>>();

    @Override
    protected Set<Column<ReserveOfferDTO, ?>> allColumns() {
        return columns;
    }

    @Override
    protected void setDefaults() {
        super.setDefaults();
    }

    @Override
    protected TableMetadata constructMetadata() {
        final TableMetadata metadata = new TableMetadata();

        // TODO Consider a predefined set of ReferenceData to be held in a common package

        // Dispatch Status -- option values as per values in db table MKTDISPATCHSTATUS
        metadata.addColumnMetadata(UiMessages.INSTANCE.dispatch_status(), new String[] {UiMessages.INSTANCE.market(), UiMessages.INSTANCE.not_qualified(), UiMessages.INSTANCE.fixed()}, new String[] {"MARKET", "NOT QUALIFIED", "FIXED"});

        return metadata;
    }

    @Override
    protected void initializeStructure(TableMetadata metadata, ListHandler<ReserveOfferDTO> sortHandler, DisplayMode currentDisplayMode) {
        addHourColumn(sortHandler);
        addDispatchStatusColumn(metadata, sortHandler, currentDisplayMode);
        addPriceMwColumn(metadata, currentDisplayMode);
    }

    protected void addHourColumn(ListHandler<ReserveOfferDTO> sortHandler) {
        final Column<ReserveOfferDTO, String> hourColumn = new Column<ReserveOfferDTO, String>(new TextCell()) {

            @Override
            public String getValue(ReserveOfferDTO reserveOffer) {
                String result = "";
                final String isoDateTime = reserveOffer.getDateTime();
                if (isoDateTime != null && !isoDateTime.isEmpty()) {
                    final Date dateTime = TimeUtil.isoToDate(isoDateTime);
                    if (dateTime != null) {
                        result = TimeUtil.dateToHour(dateTime);
                    }
                }
                return result;
            }

        };
        hourColumn.setSortable(true);
        sortHandler.setComparator(hourColumn, new Comparator<ReserveOfferDTO>() {
            @Override
            public int compare(ReserveOfferDTO ro1, ReserveOfferDTO ro2) {
                return ro1.getDateTime().compareTo(ro2.getDateTime());
            }
        });

        // We know that the data is sorted by hour by default.
        getColumnSortList().push(hourColumn);

        addColumn(hourColumn, UiMessages.INSTANCE.hour());
        setColumnWidth(hourColumn, 45, Unit.PX);
        setColumnHorizontalAlignment(hourColumn, HasHorizontalAlignment.ALIGN_RIGHT);
    }

    protected void addDispatchStatusColumn(TableMetadata metadata, ListHandler<ReserveOfferDTO> sortHandler, DisplayMode currentDisplayMode) {
        final ReferenceData refData = metadata.allColumnMetadata().get(UiMessages.INSTANCE.dispatch_status());

        Column<ReserveOfferDTO, String> dispatchStatusColumn;
        Cell<String> cell;
        if (isInEditMode(currentDisplayMode)) {
            cell = new ReferenceDataBackedSelectionCell(refData);
        } else {
            cell = new TextCell();
        }
        dispatchStatusColumn = new Column<ReserveOfferDTO, String>(cell) {

            @Override
            public String getValue(ReserveOfferDTO reserveOffer) {
                return refData.getDisplayValueForSubmitValue(reserveOffer.getDispatchStatus());
            }

        };
        dispatchStatusColumn.setSortable(true);
        sortHandler.setComparator(dispatchStatusColumn, new Comparator<ReserveOfferDTO>() {
            @Override
            public int compare(ReserveOfferDTO eo1, ReserveOfferDTO eo2) {
                return eo1.getDateTime().compareTo(eo2.getDateTime());
            }
        });

        addColumn(dispatchStatusColumn, UiMessages.INSTANCE.dispatch_status());
        setColumnWidth(dispatchStatusColumn, 100, Unit.PX);
        setColumnHorizontalAlignment(dispatchStatusColumn, HasHorizontalAlignment.ALIGN_RIGHT);
    }

    protected void addPriceMwColumn(TableMetadata metadata, DisplayMode currentDisplayMode) {

        // Construct a composite cell for energy offers that includes a pair of text inputs
        final List<HasCell<ReserveOfferDTO, ?>> columns = new ArrayList<
                HasCell<ReserveOfferDTO, ?>>();

        // Price
        final Column<ReserveOfferDTO, String> priceColumn = generatePriceColumn(currentDisplayMode);
        columns.add(priceColumn);

        // MW
        final Column<ReserveOfferDTO, String> mwColumn = generateMwColumn(currentDisplayMode);
        columns.add(mwColumn);

        // Composite
        final CompositeCell<ReserveOfferDTO> priceMwColumnInnards = generateCompositeCell(columns);

        final IdentityColumn<ReserveOfferDTO> priceMwColumn = new IdentityColumn<ReserveOfferDTO>(priceMwColumnInnards);

        addColumn(priceMwColumn, UiMessages.INSTANCE.price_mw_header());
        setColumnWidth(priceMwColumn, 7, Unit.EM);
        setColumnHorizontalAlignment(priceMwColumn, HasHorizontalAlignment.ALIGN_RIGHT);
    }

    protected Column<ReserveOfferDTO, String> generatePriceColumn(DisplayMode currentDisplayMode) {
        Column<ReserveOfferDTO, String> priceColumn;
        if (isInEditMode(currentDisplayMode)) {
            priceColumn = new AbstractValidatableColumn<ReserveOfferDTO>(2, this) {

                @Override
                public String getValue(ReserveOfferDTO reserveOffer) {
                    return obtainPriceValue(reserveOffer, false);
                }

                @Override
                protected void doUpdate(int index, ReserveOfferDTO reserveOffer, String value) {
                    // number format exceptions should be caught and handled by event bus's handle method
                    final double valueAsDouble = NumberFormat.getDecimalFormat().parse(value);
                    final BigDecimal price = BigDecimal.valueOf(valueAsDouble);
                    reserveOffer.setPrice(price);
                }

            };
        } else {
            priceColumn = new Column<ReserveOfferDTO, String>(new TextCell()) {

                @Override
                public String getValue(ReserveOfferDTO reserveOffer) {
                    return obtainPriceValue(reserveOffer, true);
                }
            };
        }
        return priceColumn;
    }

    private String obtainPriceValue(ReserveOfferDTO reserveOffer, boolean withCurrency) {
        String result = "";
        if (reserveOffer != null) {
            final BigDecimal price = reserveOffer.getPrice();
            if (price != null) {
                final double value = price.doubleValue();
                if (withCurrency) {
                    result = NumberFormat.getCurrencyFormat().format(value);
                } else {
                    result = NumberFormat.getDecimalFormat().format(value);
                }
            }
        }
        return result;
    }

    protected Column<ReserveOfferDTO, String> generateMwColumn(DisplayMode currentDisplayMode) {
        Column<ReserveOfferDTO, String> mwColumn;

        if (isInEditMode(currentDisplayMode)) {
            mwColumn = new AbstractValidatableColumn<ReserveOfferDTO>(2, this) {

                @Override
                public String getValue(ReserveOfferDTO reserveOffer) {
                    return obtainMwValue(reserveOffer);
                }

                @Override
                protected void doUpdate(int index, ReserveOfferDTO reserveOffer, String value) {
                    // number format exceptions should be caught and handled by event bus's handle method
                    final double valueAsDouble = NumberFormat.getDecimalFormat().parse(value);
                    final BigDecimal mw = BigDecimal.valueOf(valueAsDouble);
                    reserveOffer.setFixedMW(mw);
                }

            };
        } else {
            mwColumn = new Column<ReserveOfferDTO, String>(new TextCell()) {

                @Override
                public String getValue(ReserveOfferDTO reserveOffer) {
                    return obtainMwValue(reserveOffer);
                }

            };
        }

        return mwColumn;
    }

    private String obtainMwValue(ReserveOfferDTO reserveOffer) {
        String result = "";
        if (reserveOffer != null) {
            final BigDecimal mw = reserveOffer.getFixedMW();
            if (mw != null) {
                result = String.valueOf(mw.doubleValue());
            }
        }
        return result;
    }

}
