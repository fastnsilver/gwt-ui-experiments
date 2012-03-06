package name.cphillipson.experimental.gwt.client.module.main;

/**
 * MVP4G equivalent of a "place service".  Basically responsible for building tokens used by GWT's History feature taking
 * into account a navigation event's name and params.
 * @author cphillipson
 *
 */
public class EmktPlaceService extends com.mvp4g.client.history.PlaceService {

    @Override
    public String tokenize( String eventName, String param ) {
        //always add the paramSeparator since "/" is used for module separator and paramSeparator
        String token = eventName + getParamSeparator();
        if ( param != null && param.length() > 0 ) {
            token = token + param;
        }
        return token;
    }

    @Override
    protected String getParamSeparator() {
        return "/";
    }

}
