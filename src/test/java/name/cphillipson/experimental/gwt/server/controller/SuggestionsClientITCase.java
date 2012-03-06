package name.cphillipson.experimental.gwt.server.controller;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import name.cphillipson.experimental.config.RestClientContext;
import name.cphillipson.experimental.gwt.shared.Endpoints;
import name.cphillipson.experimental.gwt.shared.bean.SuggestionsPayload;

@ContextConfiguration(classes={ RestClientContext.class }, loader=AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SuggestionsClientITCase {

    private static final String SCHEME = "http";
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private static final String QUERY_KEY = "q";
    private static final String INDEX_FROM_KEY = "indexFrom";
    private static final String INDEX_TO_KEY = "indexTo";;

    private Logger log = LoggerFactory.getLogger(SuggestionsClientITCase.class);

    @Inject
    RestTemplate restTemplate;

    @Test
    public void testGetResources() {

        // Case 1:  Discover all resources using * (asterisk), first 25
        // -- the total # of resources in TestData is 250
        // -- the total # of options returned should be constrained by indexTo - indexFrom = 25
        happyPathAssertions(Endpoints.GET_RESOURCES, "*", 0, 24, 250, 25);

    }

    @Test
    public void testGetLocations() {

        // Case 1:  Discover all resources using * (asterisk), first 25
        // -- the total # of locations in TestData is 4316
        // -- the total # of options returned should be constrained by indexTo - indexFrom = 25
        happyPathAssertions(Endpoints.GET_LOCATIONS, "*", 0, 24, 4316, 25);

    }

    private void happyPathAssertions(String endpointUrl, String query, int indexFrom, int indexTo, int expectedTotal, int expectedOptionsPerPage) {
        final URI uri = buildUri(query, indexFrom, indexTo, endpointUrl);
        final HttpEntity<SuggestionsPayload> response = obtainResponse(uri);
        Assert.assertTrue(response.hasBody());
        Assert.assertEquals(expectedTotal, response.getBody().getTotalSize());
        Assert.assertEquals(expectedOptionsPerPage, response.getBody().getOptions().size());
    }

    private URI buildUri(String query, int indexFrom, int indexTo, String endPointUrl) {
        final UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                .scheme(SCHEME).host(HOST).port(PORT).path(Endpoints.CLIENT_REST_PREFIX + endPointUrl)
                .queryParam(QUERY_KEY, query)
                .queryParam(INDEX_FROM_KEY, indexFrom)
                .queryParam(INDEX_TO_KEY, indexTo)
                .build()
                .encode();

        final URI uri = uriComponents.toUri();
        return uri;
    }

    private HttpEntity<SuggestionsPayload> obtainResponse(URI uri) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON}));
        requestHeaders.setAcceptCharset(Arrays.asList(new Charset[] {Charset.forName("UTF-8")}));
        final HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);
        final HttpEntity<SuggestionsPayload> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, SuggestionsPayload.class);
        return response;
    }

}
