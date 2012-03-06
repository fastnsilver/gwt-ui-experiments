package name.cphillipson.experimental.gwt.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.lang3.ArrayUtils;

public class BeanUtil {

    /**
     * Determines if two beans match based on value equality for named properties in the property array
     * @param bean1 a POJO
     * @param bean2 another POJO
     * @param properties valid field name in both beans
     * @return true if all property values defined in property array match; false otherwise
     */
    public static boolean match(Object bean1, Object bean2, String[] properties) throws Exception {
        if (bean1 == null || bean2 == null || ArrayUtils.isEmpty(properties)) {
            throw new IllegalArgumentException("One or both beans to compare were null, or the properties used for the comparison were null or empty.");
        }
        boolean result = false;
        BeanComparator c = null;
        int counter = 0;
        for (final String property: properties) {
            c = new BeanComparator(property);
            if (c.compare(bean1, bean2) == 0) {
                counter++;
            }
        }
        if (counter == properties.length) {
            result = true;
        }
        return result;
    }

    /**
     * Returns an array of field names where each corresponding field value is non-null
     * @param obj a POJO
     * @return an array of the POJO's field names where each field's value is non-null
     */
    public static String[] getNonNullValueFieldNamesForObject(Object obj) {
        final List<String> bag = new ArrayList<String>();
        final Map map = new BeanMap(obj);
        final Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() != null) {
                bag.add(pair.getKey().toString());
            }
        }
        return bag.toArray(new String[bag.size()]);
    }

    /**
     * Returns an array of field names where each corresponding field value is non-null
     * Provides for certain field exclusions.
     * @param obj a POJO
     * @param excludedFields a String[] of field names to be excluded from consideration
     * @return an array of the POJO's field names where each field's value is non-null
     */
    public static String[] getNonNullValueFieldNamesForObject(Object obj, String[] excludedFields) {
        final List<String> bag = new ArrayList<String>();
        final Map map = new BeanMap(obj);
        final Iterator it = map.entrySet().iterator();
        final List<String> exclusions = Arrays.asList(excludedFields);
        while (it.hasNext()) {
            final Map.Entry pair = (Map.Entry) it.next();
            final String fieldName = pair.getKey().toString();
            if (pair.getValue() != null && !exclusions.contains(fieldName)) {
                bag.add(fieldName);
            }
        }
        return bag.toArray(new String[bag.size()]);
    }
}
