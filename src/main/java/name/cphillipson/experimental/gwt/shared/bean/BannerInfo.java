package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;

/**
 * (Banner) payload for <code>HeaderService</code>.
 * @cphillipson
 */
public class BannerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String hostname, database, currentUser, version, appName;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
