package name.cphillipson.experimental.gwt.server.controller.stub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import name.cphillipson.experimental.gwt.client.module.common.dto.ProductType;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferDTO;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferIdDTO;
import name.cphillipson.experimental.gwt.client.module.reserve.rpc.ReserveOfferService;
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
@RequestMapping(Endpoints.SERVER_GWT_PREFIX + Endpoints.GET_RESERVE_OFFERS)
public class StubReserveOfferService extends BaseRemoteService implements ReserveOfferService {

    @Inject
    private TestData data;

    private static Logger log = LoggerFactory.getLogger(StubReserveOfferService.class);

    @Override
    // TODO Consider custom exception
    public List<ReserveOfferDTO> getReserveOffers(ReserveOfferIdDTO criteria) throws IllegalArgumentException {
        if (criteria == null) {
            throw new IllegalArgumentException("Criteria may not be null");
        }

        // convert ReserveOfferIdDTO into ReserveOfferDTO
        final ReserveOfferDTO crit = new ReserveOfferDTO();
        crit.setResourceName(criteria.getResourceName());
        crit.setDateTime(criteria.getDate());
        crit.setMarketType(criteria.getMarketType());

        final List<ReserveOfferDTO> results = new ArrayList<ReserveOfferDTO>();
        final String[] properties = BeanUtil.getNonNullValueFieldNamesForObject(crit, new String[] { "class", "dateTime", "price", "fixedMW", "dispatchStatus" });

        try {

            final ProductType type = ProductType.fromValue(criteria.getProductType());
            List<ReserveOfferDTO> offers = new ArrayList<ReserveOfferDTO>();
            switch (type) {
                case REGUP: offers = data.getRegUpOffers(); break;
                case REGDN: offers = data.getRegDnOffers(); break;
                case SPIN: offers = data.getSpinOffers(); break;
                case SUPP: offers = data.getSuppOffers(); break;
            }

            // first pass, find the records where resource names match
            final List<ReserveOfferDTO> candidates = new ArrayList<ReserveOfferDTO>();
            for (final ReserveOfferDTO dto : offers) {
                if (BeanUtil.match(crit, dto, new String[] {"resourceName"})) {
                    candidates.add(dto);
                }
            }

            // second pass, cull for hours in criteria's date
            for (final ReserveOfferDTO dto : candidates) {
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
