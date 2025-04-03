package app;

import app.Web.dto.AddBillRequest;
import app.model.Bill;
import app.model.BillType;
import app.repository.BillRepository;
import app.repository.UserRepository;
import app.service.BillService;
import app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class PayBillITest {
    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillRepository billRepository;

    @Test
    void payBill_successful() {
        AddBillRequest request = AddBillRequest.builder()
                .userId(UUID.randomUUID())
                .billNumber("12312312312")
                .billType(BillType.ELECTRICITY)
                .build();

        billService.addBill(request);

        Optional<Bill> optionalBill = billRepository.findByBillNumber(request.getBillNumber());

        Bill bill = optionalBill.get();

        billService.payBill(bill.getId());

        assertThat(billRepository.findAll().get(0).isPaid()).isTrue();
        assertThat(billRepository.findAll().get(0).isPaid()).isNotNull();
    }
}
