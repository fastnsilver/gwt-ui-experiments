package name.cphillipson.experimental.gwt.shared.bean;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import name.cphillipson.experimental.gwt.shared.i18n.I18NConstants;


/**
 * A type of operation that will be executed via an HTTP request.
 * @author cphillipson
 *
 */
public enum Operation {

    NONE("no-op", -1),
    VIEW(I18NConstants.VIEW, 0),
    ADD(I18NConstants.ADD, 10),
    EDIT(I18NConstants.EDIT, 20),
    COPY(I18NConstants.COPY, 40),
    DELETE(I18NConstants.DELETE, 50),
    DOWNLOAD(I18NConstants.DOWNLOAD, 30),
    UPLOAD(I18NConstants.UPLOAD, 60),
    RESET(I18NConstants.RESET, 70),
    SAVE(I18NConstants.SAVE, 80);

    private String code = null;
    private int order = -1;

    // keeps operations ordered by {@link Operation#getOrder()}
    private static Comparator<Operation> comparator = new Comparator<Operation>() {

        @Override
        public int compare(Operation op1, Operation op2) {
            int comparison = -1;
            if (op1 == op2 || op1.getOrder() == op2.getOrder()) {
                comparison = 0;
            } else if (op1.getOrder() > op2.getOrder()){
                comparison = 1;
            }
            return comparison;
        }

    };

    private Operation(String code, int order) {
        this.code = code;
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    public int getOrder() {
        return order;
    }

    public static Operation getDefault() {
        return VIEW;
    }

    public static Set<Operation> orderedValues() {
        final Set<Operation> result = new TreeSet<Operation>(comparator);
        for (final Operation op: Operation.values()) {
            result.add(op);
        }
        return result;
    }
}
