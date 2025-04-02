package app.service;

import app.model.Bill;
import app.model.User;
import app.repository.BillRepository;
import app.Web.dto.AddBillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final UserService userService;

    @Autowired
    public BillService(BillRepository billRepository,@Lazy UserService userService) {
        this.billRepository = billRepository;
        this.userService = userService;
    }


    public void payBill(UUID billId) {
        Bill bill = getById(billId);

        bill.setPaid(true);
        bill.setPaidOn(LocalDate.now());

        billRepository.save(bill);
    }

    public void addBill(AddBillRequest addBillRequest) {

        User user = userService.checkForUser(addBillRequest);

        Optional<Bill> optionalBill = billRepository.findByBillNumber(addBillRequest.getBillNumber());

        if (optionalBill.isPresent()) {
            userService.addBill(user.getUserId(), optionalBill.get());
        } else {
            Bill bill = createBill(addBillRequest);
            userService.addBill(user.getUserId(), bill);
            billRepository.save(bill);
        }
    }

    public Bill createBill(AddBillRequest addBillRequest) {

         Bill bill = Bill.builder()
                .billNumber(addBillRequest.getBillNumber())
                .billType(addBillRequest.getBillType())
                .startPeriod(LocalDate.now().minusMonths(1).withDayOfMonth(1))
                .endPeriod(LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).getMonth().length(LocalDate.now().isLeapYear())))
                .isPaid(false)
                .amount(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(30, 400)).setScale(2, RoundingMode.HALF_UP))
                .build();


        return billRepository.save(bill);
    }


    public Bill getById(UUID billId) {
        return billRepository.findById(billId).orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public void updateBill(Bill bill) {
        billRepository.save(bill);
    }
}
