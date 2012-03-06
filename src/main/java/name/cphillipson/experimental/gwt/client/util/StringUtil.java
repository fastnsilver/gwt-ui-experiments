package name.cphillipson.experimental.gwt.client.util;


public class StringUtil {

    private static final String COLON = ":";

    public static String labelize(String label) {
        return labelize(label, COLON);
    }

    public static String labelize(String label, String labelDelimiter) {
        return new StringBuilder().append(label).append(labelDelimiter).toString();
    }

    public static String obtainUrlParamValue(String url, String paramName) {
        String value = null;
        final String[] params = url.split("&");
        for (final String param: params) {
            if (param.contains(paramName)) {
                value = param.split("=")[1];
                break;
            }
        }
        return value;
    }

    public static int nullSafeStringComparator(final String one, final String two) {
        if (one == null ^ two == null) {
            return one == null ? -1 : 1;
        }

        if (one == null && two == null) {
            return 0;
        }

        return one.compareToIgnoreCase(two);
    }

}
