package name.cphillipson.experimental.gwt.server.i18n;


import java.util.Collection;
import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Wrapper for Spring's {@link MessageSource} API
 * <p>
 * Used method variants to obtain a localized value for a specified bundle key.
 * If no locale is supplied, then the request is consulted for the current
 * locale delegating to a LocaleResolver. If arguments are supplied they are
 * substituted in the order as specified by the bundle entry (by locale).
 * 
 * @author cphillipson
 * 
 */
public class MessageHelper {

    private final MessageSource messageSource;

    /**
     * Construct helper with Spring's <code>MessageSource</code>
     * @param messageSource Spring's l10n messaging support
     */
    public MessageHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Obtains a localized value (used when bundle value has no arguments)
     * 
     * @param code
     *            a bundle key
     * @return a localized value matching the <em>code</em> and locale from
     *         request
     */
    public String obtainLocalizedValue(String code) {
        return obtainLocalizedValue(code, (Object[]) null);
    }

    /**
     * Obtains a localized value (used when bundle value has one or more
     * arguments)
     * <p>
     * Arguments passed as <tt>Object[]</tt>
     * 
     * @param code
     *            a bundle key
     * @param args
     *            one or more arguments to be (index order) substituted in the
     *            localized value
     * @return a localized value matching the <em>code</em> and locale from
     *         request
     */
    public String obtainLocalizedValue(String code, Object[] args) {
        return messageSource.getMessage(code, args, code, LocaleHelper.getCurrentLocale());
    }

    /**
     * Obtains a localized value (used when bundle value has one or more
     * arguments)
     * <p>
     * Arguments passed as <tt>Collection<String></tt>. Ultimately, delegates to
     * {@link MessageHelper#obtainLocalizedValue(String, Object[])}
     * 
     * @param code
     *            a bundle key
     * @param args
     *            one or more arguments to be (index order) substituted in the
     *            localized value
     * @return a localized value matching the <em>code</em> and locale from
     *         request
     */
    public String obtainLocalizedValue(String code, Collection<String> args) {
        return obtainLocalizedValue(code, args.toArray(new String[args.size()]));
    }

    /**
     * Obtains a localized value (used when you want to obtain a localized value
     * where the locale may differ from that obtained from request)
     * 
     * @param code
     *            a bundle key
     * @param args
     *            one or more arguments to be (index order) substituted in the
     *            localized value
     * @param locale
     *            a user-defined locale
     * @return a localized value matching the <em>code</em> and user-defined
     *         locale
     */
    public String obtainLocalizedValue(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, code, locale);
    }

}
