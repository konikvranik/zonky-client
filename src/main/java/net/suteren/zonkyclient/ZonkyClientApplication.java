package net.suteren.zonkyclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple Zonky marketplace client, returning every 5 minutes new Loans from Zonky marketplace.
 *
 * @author Petr Vranik
 */
@SpringBootApplication
public class ZonkyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZonkyClientApplication.class, args);
    }
}
