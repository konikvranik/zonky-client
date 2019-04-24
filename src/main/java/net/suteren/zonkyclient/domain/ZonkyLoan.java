package net.suteren.zonkyclient.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.time.OffsetDateTime;


/**
 * Domain object representing loan on marketplace. For simplicity there are some fields and some documentation stripped.
 *
 * @author Petr Vranik
 */
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZonkyLoan {
    /**
     * ID of the loan
     */
    private Long id;

    /**
     * Name of the loan.
     * Example: "Loan refinancing"
     */
    private String name;

    /**
     * Short story of the loan. Usually some story about the purpose of a loan that attracts investors.
     * Example: "Dear investors, ..."
     */
    private String story;

    /**
     * Borrower's nickname.
     * Example: "zonky0"
     */
    private String nickName;

    /**
     * Loan term (in months).
     */
    private Integer termInMonths;

    /**
     * Interest rate for investors
     */
    private Float interestRate;

    /**
     * Revenue rate for investors (loan interest rate - investment fee).
     */
    private Float revenueRate;

    /**
     * TODO: Other Javadoc bypassed for simplicity. In production code it should'nt happen.
     */
    private Float annuity;
    private String rating;
    private Boolean topped;
    private Integer amount;
    private Integer remainingInvestment;
    private Float investmentRate;
    private Boolean covered;
    private Integer reservedAmount;
    private Integer zonkyPlusAmount;
    private OffsetDateTime datePublished;
    private Boolean published;
    private OffsetDateTime deadline;

    // TODO: Stripped other fields for simplicity of this demo application. In production code maybe it could be
    //  required.

}
