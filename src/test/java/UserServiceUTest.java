import app.model.Bill;
import app.model.User;
import app.repository.UserRepository;
import app.service.BillService;
import app.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static app.web.TestBuilder.randomBill;
import static app.web.TestBuilder.randomUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUTest {

    @Mock
    private BillService billService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void removeBill() {
        UUID billId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Bill bill = randomBill();
        User user = randomUser();

        when(userService.getByUserId(userId)).thenReturn(Optional.of(user));
        when(billService.getById(billId)).thenReturn(bill);

        userService.removeBill(billId, userId);

        assertFalse(user.getBills().contains(bill));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getAllUserBills() {
        User user = randomUser();
        user.getBills().add(randomBill());
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));

        List<Bill> bills = userService.allUserBills(user.getUserId());

        assertNotNull(bills);
        assertEquals(1, bills.size());

    }

}
