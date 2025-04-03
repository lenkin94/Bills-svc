package app;

import app.Web.dto.AddBillRequest;
import app.model.Bill;
import app.model.BillType;
import app.model.User;
import app.repository.BillRepository;
import app.repository.UserRepository;
import app.service.BillService;
import app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class AddBillITest {

    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillRepository billRepository;

    @Test
    void addBill_successful() {
        AddBillRequest request = AddBillRequest.builder()
                .userId(UUID.randomUUID())
                .billNumber("12312312312")
                .billType(BillType.ELECTRICITY)
                .build();

        billService.addBill(request);

        assertThat(userRepository.findAll().size()).isEqualTo(1);
        assertThat(billRepository.findAll().size()).isEqualTo(1);
        assertThat(billRepository.findAll().get(0).getBillNumber()).isEqualTo("12312312312");
        assertThat(billRepository.findAll().get(0).getBillType()).isEqualTo(BillType.ELECTRICITY);
        assertThat(userRepository.findAll().get(0).getUserId()).isEqualTo(request.getUserId());
    }
}
