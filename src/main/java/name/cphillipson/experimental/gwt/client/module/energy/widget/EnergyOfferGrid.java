package name.cphillipson.experimental.gwt.client.module.energy.widget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.cphillipson.experimental.gwt.client.module.common.dto.OfferPriceMwPairDTO;
import name.cphillipson.experimental.gwt.client.module.common.view.DisplayMode;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.AbstractValidatableColumn;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.ReferenceDataBackedSelectionCell;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.ToggleableGrid;
import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferDTO;
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

public class EnergyOfferGrid extends ToggleableGrid<EnergyOfferDTO> {

    public EnergyOfferGrid() {
        super();
    }

    public EnergyOfferGrid(int pageSize, Resources resources) {
        super(pageSize, resources);
    }

    // XXX Should the "get*" service that prepares the data be responsible for generating empty:
    //     (a) rows for hours not persisted in dB?
    //     (b) columns for all points not persisted per hour in the dB?
    // XXX Should the "post*" service that submits, prune empty columns and rows before posting to dB?

    private static final int MAX_NUMBER_OF_MW_PRICE_POINTS = 10;

    private Set<Column<EnergyOfferDTO, ?>> columns = new HashSet<Column<EnergyOfferDTO, ?>>();

    @Override
    protected Set<Column<EnergyOfferDTO, ?>> allColumns() {
        return columns;
    }

    @Override
    protected TableMetadata constructMetadata() {
        final TableMetadata metadata = new TableMetadata();

        // TODO Consider a predefined set of ReferenceData to be held in a common package

        // Use Slope
        metadata.addColumnMetadata(UiMessages.INSTANCE.use_slope(), new String[] {UiMessages.INSTANCE.yes(), UiMessages.INSTANCE.no()}, new String[] {"true", "false"});

        return metadata;
    }

    @Override
    protected void initializeStructure(TableMetadata metadata, ListHandler<EnergyOfferDTO> sortHandler, DisplayMode currentDisplayMode) {
        addHourColumn(sortHandler);
        addUseSlopeColumn(metadata, sortHandler, currentDisplayMode);
        for (int i = 0; i < MAX_NUMBER_OF_MW_PRICE_POINTS; i++) {  // zero-based indexing
            addPriceMwColumn(i, currentDisplayMode);
        }
    }

    protected void addHourColumn(ListHandler<EnergyOfferDTO> sortHandler) {
        final Column<EnergyOfferDTO, String> hourColumn = new Column<EnergyOfferDTO, String>(new TextCell()) {

            @Override
            public String getValue(EnergyOfferDTO energyOffer) {
                String result = "";
                final String isoDateTime = energyOffer.getDateTime();
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
        sortHandler.setComparator(hourColumn, new Comparator<EnergyOfferDTO>() {
            @Override
            public int compare(EnergyOfferDTO eo1, EnergyOfferDTO eo2) {
                return eo1.getDateTime().compareTo(eo2.getDateTime());
            }
        });

        // We know that the data is sorted by hour by default.
        getColumnSortList().push(hourColumn);

        addColumn(hourColumn, UiMessages.INSTANCE.hour());
        setColumnWidth(hourColumn, 45, Unit.PX);
        setColumnHorizontalAlignment(hourColumn, HasHorizontalAlignment.ALIGN_RIGHT);
    }


    protected void addUseSlopeColumn(TableMetadata metadata, ListHandler<EnergyOfferDTO> sortHandler, DisplayMode currentDisplayMode) {
        final ReferenceData refData = metadata.allColumnMetadata().get(UiMessages.INSTANCE.use_slope());
        Column<EnergyOfferDTO, String> useSlopeColumn;
        Cell<String> cell;
        if (isInEditMode(currentDisplayMode)) {
            cell = new ReferenceDataBackedSelectionCell(refData);
        } else {
            cell = new TextCell();
        }
        useSlopeColumn = new Column<EnergyOfferDTO, String>(cell) {

            @Override
            public String getValue(EnergyOfferDTO energyOffer) {
                return refData.getDisplayValueForSubmitValue(Boolean.toString(energyOffer.isSlope()));
            }

        };

        useSlopeColumn.setSortable(true);
        sortHandler.setComparator(useSlopeColumn, new Comparator<EnergyOfferDTO>() {
            @Override
            public int compare(EnergyOfferDTO eo1, EnergyOfferDTO eo2) {
                return eo1.getDateTime().compareTo(eo2.getDateTime());
            }
        });

        addColumn(useSlopeColumn, UiMessages.INSTANCE.use_slope());
        setColumnWidth(useSlopeColumn, 75, Unit.PX);
        setColumnHorizontalAlignment(useSlopeColumn, HasHorizontalAlignment.ALIGN_RIGHT);
    }

    protected void addPriceMwColumn(final int colIndex, DisplayMode currentDisplayMode) {

        // Construct a composite cell for energy offers that includes a pair of text inputs
        final List<HasCell<EnergyOfferDTO, ?>> columns = new ArrayList<
                HasCell<EnergyOfferDTO, ?>>();

        // this DTO is passed along so that price and mw values for new entries are kept together
        final OfferPriceMwPairDTO newOfferPriceMwPairDTO = new OfferPriceMwPairDTO();

        // Price
        final Column<EnergyOfferDTO, String> priceColumn = generatePriceColumn(colIndex, newOfferPriceMwPairDTO, currentDisplayMode);
        columns.add(priceColumn);

        // MW
        final Column<EnergyOfferDTO, String> mwColumn = generateMwColumn(colIndex, newOfferPriceMwPairDTO, currentDisplayMode);
        columns.add(mwColumn);

        // Composite
        final CompositeCell<EnergyOfferDTO> priceMwColumnInnards = generateCompositeCell(columns);

        final IdentityColumn<EnergyOfferDTO> priceMwColumn = new IdentityColumn<EnergyOfferDTO>(priceMwColumnInnards);

        final StringBuilder colHeader = new StringBuilder();
        colHeader.append(UiMessages.INSTANCE.price_mw_header()).append(" ").append(String.valueOf(colIndex + 1));

        addColumn(priceMwColumn, colHeader.toString());
        setColumnWidth(priceMwColumn, 7, Unit.EM);
        setColumnHorizontalAlignment(priceMwColumn, HasHorizontalAlignment.ALIGN_RIGHT);
    }

    protected Column<EnergyOfferDTO, String> generatePriceColumn(final int colIndex, final OfferPriceMwPairDTO newOfferPriceMwPair, DisplayMode currentDisplayMode) {
        Column<EnergyOfferDTO, String> priceColumn;

        if (isInEditMode(currentDisplayMode)) {
            priceColumn = new AbstractValidatableColumn<EnergyOfferDTO>(2, this) {

                @Override
                public String getValue(EnergyOfferDTO energyOffer) {
                    return obtainPriceValue(colIndex, energyOffer, false);
                }


                @Override
                public void doUpdate(int index, EnergyOfferDTO energyOffer, String value) {
                    if (value != null && !value.isEmpty()) {
                        // number format exceptions should be caught and handled by event bus's handle method
                        final double valueAsDouble = NumberFormat.getDecimalFormat().parse(value);

                        final BigDecimal price = BigDecimal.valueOf(valueAsDouble);
                        final List<OfferPriceMwPairDTO> offerPriceCurve = energyOffer.getOfferPriceCurve();
                        final OfferPriceMwPairDTO offerPriceMwPairDTO = offerPriceCurve.get(colIndex);
                        if (offerPriceMwPairDTO == null) {  // we have a new price value
                            newOfferPriceMwPair.setPrice(price);
                            offerPriceCurve.add(newOfferPriceMwPair);
                        } else {
                            offerPriceMwPairDTO.setPrice(price);
                        }

                    }
                }
            };
        } else {
            priceColumn = new Column<EnergyOfferDTO, String>(new TextCell()) {

                @Override
                public String getValue(EnergyOfferDTO energyOffer) {
                    final String result = obtainPriceValue(colIndex, energyOffer, true);
                    return result;
                }
            };
        }
        return priceColumn;
    }

    private String obtainPriceValue(final int colIndex, EnergyOfferDTO energyOffer, boolean withCurrency) {
        String result = "";
        if (energyOffer != null) {
            final List<OfferPriceMwPairDTO> offerPriceCurve = energyOffer.getOfferPriceCurve();
            final int numberOfPairs = offerPriceCurve.size();
            if (colIndex < numberOfPairs) {
                final OfferPriceMwPairDTO offerPriceMwPairDTO = offerPriceCurve.get(colIndex);
                if (offerPriceMwPairDTO != null) {
                    final BigDecimal price = offerPriceMwPairDTO.getPrice();
                    if (price != null) {
                        final double value = price.doubleValue();
                        if (withCurrency) {
                            result = NumberFormat.getCurrencyFormat().format(value);
                        } else {
                            result = NumberFormat.getDecimalFormat().format(value);
                        }
                    }

                }
            }
        }
        return result;
    }

    protected Column<EnergyOfferDTO, String> generateMwColumn(final int colIndex, final OfferPriceMwPairDTO newOfferPriceMwPair, DisplayMode currentDisplayMode) {
        Column<EnergyOfferDTO, String> mwColumn;

        if (isInEditMode(currentDisplayMode)) {
            mwColumn = new AbstractValidatableColumn<EnergyOfferDTO>(2, this) {

                @Override
                public String getValue(EnergyOfferDTO energyOffer) {
                    return obtainMwValue(colIndex, energyOffer);
                }

                @Override
                public void doUpdate(int index, EnergyOfferDTO energyOffer, String value) {
                    if (value != null && !value.isEmpty()) {
                        // number format exceptions should be caught and handled by event bus's handle method
                        final double valueAsDouble = NumberFormat.getDecimalFormat().parse(value);

                        final BigDecimal mw = BigDecimal.valueOf(valueAsDouble);
                        final List<OfferPriceMwPairDTO> offerPriceCurve = energyOffer.getOfferPriceCurve();
                        final OfferPriceMwPairDTO offerPriceMwPairDTO = offerPriceCurve.get(colIndex);
                        if (offerPriceMwPairDTO == null) {  // we have a new price value
                            newOfferPriceMwPair.setMw(mw);
                            offerPriceCurve.add(newOfferPriceMwPair);
                        } else {
                            offerPriceMwPairDTO.setMw(mw);
                        }

                    }
                }
            };
        } else {
            mwColumn = new Column<EnergyOfferDTO, String>(new TextCell()) {

                @Override
                public String getValue(EnergyOfferDTO energyOffer) {
                    final String result = obtainMwValue(colIndex, energyOffer);
                    return result;
                }
            };
        }
        return mwColumn;
    }

    private String obtainMwValue(final int colIndex, EnergyOfferDTO energyOffer) {
        String result = "";
        if (energyOffer != null) {
            final List<OfferPriceMwPairDTO> offerPriceCurve = energyOffer.getOfferPriceCurve();
            final int numberOfPairs = offerPriceCurve.size();
            if (colIndex < numberOfPairs) {
                final OfferPriceMwPairDTO offerPriceMwPairDTO = offerPriceCurve.get(colIndex);
                if (offerPriceMwPairDTO != null) {
                    final BigDecimal mw = offerPriceMwPairDTO.getMw();
                    if (mw != null) {
                        result = String.valueOf(mw.doubleValue());
                    }
                }
            }
        }
        return result;
    }

}
