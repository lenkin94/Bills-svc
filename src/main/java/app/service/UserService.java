package app.service;

import app.Web.dto.AddBillRequest;
import app.model.Bill;
import app.model.User;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BillService billService;

    @Autowired
    public UserService(UserRepository userRepository, BillService billService) {
        this.userRepository = userRepository;
        this.billService = billService;
    }

    public Optional<User> getByUserId(UUID userId) {
        return userRepository.findByUserId(userId);
    }

    public User checkForUser(AddBillRequest addBillRequest) {
        Optional<User> user = getByUserId(addBillRequest.getUserId());

        return user.orElseGet(() -> userRepository.save(User.builder()
                .userId(addBillRequest.getUserId())
                .bills(new ArrayList<>())
                .build()));

    }

    public void addBill(UUID userId, Bill bill) {
        Optional<User> optUser = getByUserId(userId);

        if (optUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optUser.get();

        if (user.getBills().contains(bill)) {
            throw new RuntimeException("Bill with bill number '%s' already exists".formatted(bill.getBillNumber()));
        }

        user.getBills().add(bill);
        userRepository.save(user);
    }

    public void removeBill(UUID billId, UUID userId) {
        Optional<User> optionalUser = getByUserId(userId);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Bill bill = billService.getById(billId);
        User user = optionalUser.get();
        user.getBills().remove(bill);
        userRepository.save(user);
    }

    public List<Bill> allUserBills(UUID userId) {
        Optional<User> optUser = getByUserId(userId);
        if (optUser.isEmpty()) {
            return new ArrayList<>();
        }
        User user = optUser.get();

        return user.getBills();
    }
}
