package name.cphillipson.experimental.gwt.server.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class AppUtil {

    public static String getHostName() {
        String server = null;

        // Obtain server name of local host
        try {
            final InetAddress localMachine = InetAddress.getLocalHost();
            server = localMachine.getHostName();
        } catch (final UnknownHostException e) {
            server = "Unknown";
        }
        return server;
    }

    public static String getHostName(String ipAddress) {
        String server = null;
        try {
            // Get hostname by textual representation of IP address
            final InetAddress addr = InetAddress.getByName(ipAddress);
            server = addr.getHostName();
        } catch (final UnknownHostException e) {
            server = ipAddress;
        }
        return server;

    }

    public static String newUuid() {
        return UUID.randomUUID().toString();
    }

    // Counts the decimal places of a given double value.
    // ex: 89.90 , 2
    // 89.906 , 3
    // 89.9 , 1
    public static int countDecimalPlaces(double value) {
        if (Math.round(value) == value) {
            return 0;
        }
        final String s = Double.toString(value);
        final int index = s.indexOf('.');
        if (index < 0) {
            return 0;
        }
        return s.length() - 1 - index;
    }

}
