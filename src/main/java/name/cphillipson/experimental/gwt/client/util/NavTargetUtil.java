package name.cphillipson.experimental.gwt.client.util;

import java.util.ArrayList;
import java.util.List;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.shared.bean.Navigable;
import name.cphillipson.experimental.gwt.shared.bean.Operation;

/**
 * <p>Provides means for:
 * <ul><li>a) transforming a String token into a <code>NavTarget</code></li>
 * <li>b) determining (semantically) what kind of operation the target performs</li></p>
 * @author cphillipson
 *
 */
public class NavTargetUtil {

    public static NavTarget toNavTarget(String eventName, String token) {
        NavTarget target = null;
        final String[] paramValuePairs = token.split("&");
        final List<String> paramNames = new ArrayList<String>();
        final List<String> paramValues = new ArrayList<String>();
        String[] paramValueParts = null;
        for (final String paramValue: paramValuePairs) {
            paramValueParts = paramValue.split("=");
            paramNames.add(paramValueParts[0]);
            paramValues.add(paramValueParts[1]);
        }
        for (final Operation op: Operation.values()) {
            if (eventName.startsWith(op.getCode())) {
                paramNames.add(Navigable.OP_KEY);
                paramValues.add(op.getCode());
                break;
            }
        }
        target = new NavTarget(paramNames.toArray(new String[paramNames.size()]),
                paramValues.toArray(new String[paramValues.size()]));
        return target;
    }

    public static boolean toView(NavTarget target) {
        return target.contains(Operation.VIEW.getCode());
    }

    public static boolean toAdd(NavTarget target) {
        return target.contains(Operation.ADD.getCode());
    }

    public static boolean toEdit(NavTarget target) {
        return target.contains(Operation.EDIT.getCode());
    }

    public static boolean toCopy(NavTarget target) {
        return target.contains(Operation.COPY.getCode());
    }

    public static boolean toDelete(NavTarget target) {
        return target.contains(Operation.DELETE.getCode());
    }

    public static boolean toDownload(NavTarget target) {
        return target.contains(Operation.DOWNLOAD.getCode());
    }

    public static boolean toUpload(NavTarget target) {
        return target.contains(Operation.UPLOAD.getCode());
    }
}
