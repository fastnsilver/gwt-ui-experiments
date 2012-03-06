package name.cphillipson.experimental.gwt.server.controller.stub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import name.cphillipson.experimental.gwt.server.dao.TestData;
import name.cphillipson.experimental.gwt.shared.Endpoints;
import name.cphillipson.experimental.gwt.shared.bean.SuggestionsPayload;
import name.cphillipson.experimental.gwt.shared.bean.SuggestionsPayload.SuggestionOption;

@Controller
@RequestMapping(Endpoints.SERVER_REST_PREFIX)
public class StubSuggestionsController {

    @Inject
    private TestData testData;

    @RequestMapping(value=Endpoints.GET_RESOURCES, method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody SuggestionsPayload getResources(@RequestParam(value="q") String query, @RequestParam int indexFrom, @RequestParam int indexTo) {
        return makeRequest(query, indexFrom, indexTo, testData.getResources());
    }

    @RequestMapping(value=Endpoints.GET_LOCATIONS, method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody SuggestionsPayload getLocations(@RequestParam(value="q") String query, @RequestParam int indexFrom, @RequestParam int indexTo) {
        return makeRequest(query, indexFrom, indexTo, testData.getLocations());
    }

    private SuggestionsPayload makeRequest(String query, int indexFrom, int indexTo, String[] data) {
        int count = 0;
        final List<SuggestionOption> possibilities = new ArrayList<SuggestionOption>();
        for (final String resourceName: data) {
            final String key = resourceName.toLowerCase();
            final int has = key.indexOf(query.toLowerCase());

            if (!query.isEmpty() && (query.equals("*") || has >= 0)) {
                final SuggestionOption possibility = new SuggestionOption();
                possibility.setDisplayName(resourceName);
                possibility.setValue(resourceName);   // shouldn't this be a unique id?  e.g., resourceid
                possibilities.add(possibility);
                count++;
            }
        }

        // sort the suggestions by display name
        Collections.sort(possibilities, new Comparator<SuggestionOption>() {

            @Override
            public int compare(SuggestionOption o1, SuggestionOption o2) {
                final int comparison = o1.getDisplayName().compareTo(o2.getDisplayName());
                return comparison;
            }

        });

        final List<SuggestionOption> options = new ArrayList<SuggestionOption>();
        if (possibilities.size() > 0) {
            final int end = count - 1 > indexTo ? indexTo : count - 1;
            for (int i = indexFrom; i <= end; i++) {
                options.add(possibilities.get(i));
            }

        }

        final SuggestionsPayload result = new SuggestionsPayload();
        result.setTotalSize(count);
        result.setOptions(options);
        return result;
    }

}
