package name.cphillipson.experimental.gwt.client.module.common.validation;

import name.cphillipson.experimental.gwt.client.module.common.dto.BidPriceMwPairDTO;
import name.cphillipson.experimental.gwt.client.module.common.dto.OfferPriceMwPairDTO;
import name.cphillipson.experimental.gwt.client.module.common.dto.PriceMwPairDTO;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

import javax.validation.Validator;

/**
 * Specify all JSR-303 annotated beans here that you wish to be validated client-side
 * @author cphillipson
 *
 */
public class ValidatorFactory extends AbstractGwtValidatorFactory {

    /**
     * Validator marker. Only the classes listed in the {@link GwtValidation} annotation can be validated.
     */
    @GwtValidation(value = { ReserveOfferDTO.class, PriceMwPairDTO.class, BidPriceMwPairDTO.class, OfferPriceMwPairDTO.class })
    public interface MyValidator extends Validator {
    }

    @Override
    public AbstractGwtValidator createValidator() {
        return GWT.create(MyValidator.class);
    }

}
