package name.cphillipson.experimental.gwt.server.controller.stub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferDTO;
import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferIdDTO;
import name.cphillipson.experimental.gwt.client.module.energy.rpc.EnergyOfferService;
import name.cphillipson.experimental.gwt.server.controller.BaseRemoteService;
import name.cphillipson.experimental.gwt.server.dao.TestData;
import name.cphillipson.experimental.gwt.server.util.BeanUtil;
import name.cphillipson.experimental.gwt.server.util.TimeUtil;
import name.cphillipson.experimental.gwt.shared.Endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Endpoints.SERVER_GWT_PREFIX + Endpoints.GET_ENERGY_OFFERS)
public class StubEnergyOfferService extends BaseRemoteService implements EnergyOfferService {

    @Inject
    private TestData data;

    private static Logger log = LoggerFactory.getLogger(StubEnergyOfferService.class);

    @Override
    // TODO Consider custom exception
    public List<EnergyOfferDTO> getEnergyOffers(EnergyOfferIdDTO criteria) throws IllegalArgumentException {
        if (criteria == null) {
            throw new IllegalArgumentException("Criteria may not be null");
        }

        // convert EnergyOfferIdDTO into EnergyOfferDTO
        final EnergyOfferDTO crit = new EnergyOfferDTO();
        crit.setResourceName(criteria.getResourceName());
        crit.setDateTime(criteria.getDateTime());
        crit.setMarketType(criteria.getMarketType());

        final List<EnergyOfferDTO> results = new ArrayList<EnergyOfferDTO>();
        final String[] properties = BeanUtil.getNonNullValueFieldNamesForObject(crit, new String[] { "class", "dateTime", "slope", "offerPriceCurve" });

        try {
            // first pass, find the records where resource names match
            final List<EnergyOfferDTO> candidates = new ArrayList<EnergyOfferDTO>();
            for (final EnergyOfferDTO dto : data.getEnergyOffers()) {
                if (BeanUtil.match(crit, dto, new String[] {"resourceName"})) {
                    candidates.add(dto);
                }
            }

            // second pass, cull for hours in criteria's date
            for (final EnergyOfferDTO dto : candidates) {
                if (BeanUtil.match(crit, dto, properties)) {
                    final Date rawDtoDate = TimeUtil.isoToDate(dto.getDateTime());  // convert ISO String format to java.util.Date
                    final String day = TimeUtil.dateToIsoDay(rawDtoDate);  // convert j.u.D into String format YYYY-MM-dd
                    // let's compare date part now
                    if (day.equals(crit.getDateTime())) {
                        results.add(dto);
                    }
                }
            }
        } catch (final Exception e) {
            log.error("Problem matching criteria.\n", e);
        }
        return results;
    }


}
