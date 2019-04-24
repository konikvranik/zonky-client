package net.suteren.zonkyclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Main application configuration.
 *
 * @author Petr Vranik
 */
@Configuration
@EnableScheduling
public class ApplicationConfiguration {

    @Value("${zonky.marketplace.uri}")
    private String marketplaceUrl;

    /**
     * @return Builder for marketplace URI. Base URL is obtained from configuration property "zonky.marketplace.uri"
     * and then there are appended filtering criteria when getting records.
     */
    @Bean
    public UriComponentsBuilder getMarketplaceUriBuilder() {
        return UriComponentsBuilder.fromUriString(marketplaceUrl);
    }
}
