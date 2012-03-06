package name.cphillipson.experimental.gwt.server.nav.stub;



import name.cphillipson.experimental.gwt.server.dao.TestData;
import name.cphillipson.experimental.gwt.server.nav.VirtualNavigationConstructor;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.i18n.I18NConstants;

public class StubVirtualNavigationConstructor extends VirtualNavigationConstructor implements StubDataProvider {

    private TestData dataSource;

    @Override
    protected void seedLocations(NavNode day) {
        final NavNode locations = builder.buildNavNode(day, localize(I18NConstants.LOCATIONS), NodeType.LOCATIONS);
        String locationName = null;
        for (int i = 0; i < LOCATION_NUMBER_THRESHOLD; i++) {
            locationName = dataSource.getLocation(i);
            seedLocation(locations, locationName);
        }
    }

    @Override
    public void setDataSource(TestData dataSource) {
        this.dataSource = dataSource;
        dataSource.setThresholds(OPERATING_DAYS_THRESHOLD, RESOURCE_NUMBER_THRESHOLD, LOCATION_NUMBER_THRESHOLD);
    }

}