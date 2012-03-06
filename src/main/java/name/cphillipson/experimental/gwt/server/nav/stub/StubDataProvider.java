package name.cphillipson.experimental.gwt.server.nav.stub;

import name.cphillipson.experimental.gwt.server.dao.TestData;

/**
 * Stub constructors must set an in-memory datasource.
 * @author cphillipson
 *
 */
public interface StubDataProvider {
    void setDataSource(TestData testData);
}
