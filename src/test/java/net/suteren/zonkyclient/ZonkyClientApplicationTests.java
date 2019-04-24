package net.suteren.zonkyclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.suteren.zonkyclient.domain.ZonkyLoan;
import net.suteren.zonkyclient.logic.LoanWatchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toSet;
import static net.suteren.zonkyclient.ApplicationTestConfiguration.TEST_BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(LoanWatchService.class)
@Import(ApplicationTestConfiguration.class)
public class ZonkyClientApplicationTests {

    @Autowired
    private LoanWatchService loanWatchService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUniqueness() {

        AtomicInteger requestSeq = new AtomicInteger();

        this.server.expect(ExpectedCount.twice(), requestTo(startsWith(TEST_BASE_URL)))
                .andRespond(request -> makeResponse(request, getLoansForRequestNumber(requestSeq.getAndIncrement())));

        Set<ZonkyLoan> loans = this.loanWatchService.getNewLoans();
        assertThat(loans)
                .isNotEmpty()
                .hasSize(7)
                .contains(makeLoan(1L))
                .contains(makeLoan(2L))
                .contains(makeLoan(3L))
                .contains(makeLoan(4L))
                .contains(makeLoan(5L))
                .contains(makeLoan(6L))
                .contains(makeLoan(7L));

        loans = this.loanWatchService.getNewLoans();
        assertThat(loans)
                .isNotEmpty()
                .hasSize(4)
                .contains(makeLoan(8L))
                .contains(makeLoan(9L))
                .contains(makeLoan(10L))
                .contains(makeLoan(11L));

    }

    private Set<ZonkyLoan> getLoansForRequestNumber(int requestSeq) {
        if (requestSeq == 0) {
            return makeLoans(1L, 2L, 3L, 4L, 5L, 6L, 7L);
        } else {
            return makeLoans(6L, 7L, 8L, 9L, 10L, 11L);
        }
    }

    private ClientHttpResponse makeResponse(ClientHttpRequest request, Set<ZonkyLoan> value) throws IOException {
        return withSuccess(objectMapper.writeValueAsString(value), MediaType.APPLICATION_JSON).createResponse(request);
    }

    private Set<ZonkyLoan> makeLoans(Long... ids) {
        return Arrays.stream(ids).map(this::makeLoan).collect(toSet());
    }

    private ZonkyLoan makeLoan(Long id) {
        ZonkyLoan l = new ZonkyLoan();
        l.setId(id);
        l.setName(id.toString());
        return l;
    }
}
