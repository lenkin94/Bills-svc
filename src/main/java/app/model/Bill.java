package app.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BillType billType;

    @Column(nullable = false, unique = true)
    private String billNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate startPeriod;

    @Column(nullable = false)
    private LocalDate endPeriod;

    @Column(nullable = false)
    private boolean isPaid;

    private LocalDate paidOn;

}
