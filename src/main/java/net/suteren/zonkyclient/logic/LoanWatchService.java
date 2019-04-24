package net.suteren.zonkyclient.logic;

import net.suteren.zonkyclient.domain.ZonkyLoan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpMethod.GET;

/**
 * Service responsible for returning unseen records from Zonky loan marketplace. Records last time of run and last
 * records IDs to prevent returning the same record multiple times.
 *
 * @author Petr Vranik
 */
@Service
public class LoanWatchService {

    private static final String DATE_PUBLISHED_FILTER = "datePublished";
    private static final String GT_OPERATOR = "gt";
    private static final String OPERATOR_SEPARATOR = "__";
    private final RestTemplate template;

    private Instant lastRun;

    private Set<Long> lastIds;

    private final static Logger LOG = LoggerFactory.getLogger(LoanWatchService.class);

    private final UriComponentsBuilder uriBuilder;

    public LoanWatchService(RestTemplateBuilder restTemplateBuilder, UriComponentsBuilder uriBuilder) {
        this.template = restTemplateBuilder.build();
        this.uriBuilder = uriBuilder;
    }

    /**
     * @return New loans since last run. Query just loans after last run and filter out records which was already
     * returned in last run (prevention of time overlapping).
     */
    public Set<ZonkyLoan> getNewLoans() {

        uriBuilder.replaceQuery(null);
        if (lastRun != null) {
            uriBuilder.queryParam(DATE_PUBLISHED_FILTER + OPERATOR_SEPARATOR + GT_OPERATOR,
                    lastRun.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME));
        }
        lastRun = Instant.now();

        URI uri = uriBuilder.build().toUri();
        LOG.debug("URI: {}", uri);

        ResponseEntity<Set<ZonkyLoan>> response = template.exchange(uri, GET, null,
                new ParameterizedTypeReference<Set<ZonkyLoan>>() {
                });

        // Filter-out Loans we hale already seen. (Maybe it is possible test by comparing to greatest ID from last
        // run, but no note in API description, that ID is guaranteed to increase.
        Set<ZonkyLoan> result = response.getBody().stream().filter(loan -> lastIds == null || !lastIds.contains(loan.getId())).collect(toSet());

        lastIds = result.stream().map(ZonkyLoan::getId).collect(toSet());

        return result;

    }
}
