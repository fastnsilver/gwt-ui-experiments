package name.cphillipson.experimental.gwt.client.module.common.bean;

/**
 * Information about this MUI instance
 * @author cphillipson
 *
 */
public class AboutInfo {

    private final String appName, hostname, database, version;

    public AboutInfo(String appName, String version, String hostname, String database) {
        this.appName = appName;
        this.version = version;
        this.hostname = hostname;
        this.database = database;
    }

    public String getAppName() {
        return appName;
    }

    public String getHostname() {
        return hostname;
    }

    public String getDatabase() {
        return database;
    }

    public String getVersion() {
        return version;
    }

}
