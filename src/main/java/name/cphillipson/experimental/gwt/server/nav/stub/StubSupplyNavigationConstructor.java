package name.cphillipson.experimental.gwt.server.nav.stub;

import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.RESOURCES;

import name.cphillipson.experimental.gwt.server.dao.TestData;
import name.cphillipson.experimental.gwt.server.nav.SupplyNavigationConstructor;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;

public class StubSupplyNavigationConstructor extends SupplyNavigationConstructor implements StubDataProvider {

    private TestData dataSource;

    @Override
    protected void seedResources(NavNode day) {
        final NavNode resources = builder.buildNavNode(day, localize(RESOURCES), NodeType.RESOURCES);
        String resourceName = null;
        for (int i = 0; i < RESOURCE_NUMBER_THRESHOLD; i++) {
            resourceName = dataSource.getResource(i);
            seedResource(resources, resourceName);
        }
    }

    @Override
    public void setDataSource(TestData dataSource) {
        this.dataSource = dataSource;
        dataSource.setThresholds(OPERATING_DAYS_THRESHOLD, RESOURCE_NUMBER_THRESHOLD, LOCATION_NUMBER_THRESHOLD);
    }

}