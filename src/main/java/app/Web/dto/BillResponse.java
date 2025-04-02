package app.Web.dto;

import app.model.BillType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillResponse {
    private UUID id;

    private BillType billType;

    private String billNumber;

    private BigDecimal amount;

    private LocalDate startPeriod;

    private LocalDate endPeriod;

    private boolean isPaid;

    private LocalDate paidOn;
}
