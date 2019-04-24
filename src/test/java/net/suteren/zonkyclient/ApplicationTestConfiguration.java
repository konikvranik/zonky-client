package net.suteren.zonkyclient;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Configuration for unit tests.
 *
 * @author Petr Vranik
 */
@TestConfiguration
public class ApplicationTestConfiguration {

    static final String TEST_BASE_URL = "/loans/marketplace";

    @Bean
    public UriComponentsBuilder getMarketplaceUriBuilder() {
        return UriComponentsBuilder.fromUriString(TEST_BASE_URL);
    }
}
