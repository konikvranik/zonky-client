package net.suteren.zonkyclient.logic;

import net.suteren.zonkyclient.domain.ZonkyLoan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.joining;

/**
 * Component responsible for scheduled grabbing new loans and displaying them.
 *
 * @author Petr Vranik
 */
@Component
public class LoanWatchScheduler {

    private final static Logger LOG = LoggerFactory.getLogger(LoanWatchScheduler.class);

    private final LoanWatchService loanWatchService;

    public LoanWatchScheduler(LoanWatchService loanWatchService) {
        this.loanWatchService = loanWatchService;
        LOG.debug("Scheduler instantiated");
    }

    /**
     * Triggered every 5 minutes it asks {@link LoanWatchService} for new loans and displays it through logger.
     */
    @Scheduled(fixedRateString = "PT5M")
    public void report() {
        LOG.info("\n" + loanWatchService.getNewLoans().stream()
                .map(ZonkyLoan::toString)
                .collect(joining("\n")));
    }
}
