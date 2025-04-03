package app.web;

import app.model.Bill;
import app.model.BillType;
import app.model.User;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@UtilityClass
public class TestBuilder {
    public static User randomUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .bills(new ArrayList<>())
                .build();
    }

    public static Bill randomBill() {
        return Bill.builder()
                .id(UUID.randomUUID())
                .billNumber("342423423423")
                .billType(BillType.ELECTRICITY)
                .isPaid(false)
                .startPeriod(LocalDate.now())
                .endPeriod(LocalDate.now().plusDays(1))
                .paidOn(null)
                .amount(BigDecimal.valueOf(200))
                .build();
    }
}
