package app.Web;

import app.Web.dto.AddBillRequest;
import app.Web.dto.BillResponse;
import app.Web.mapper.DtoMapper;
import app.model.Bill;
import app.model.User;
import app.service.BillService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/bills")
public class BillController {
    private final BillService billService;
    private final UserService userService;

    @Autowired
    public BillController(BillService billService, UserService userService) {
        this.billService = billService;
        this.userService = userService;
    }

    @PostMapping("add-bill")
    private ResponseEntity<Void> addBill(@RequestBody AddBillRequest addBillRequest) {
        billService.addBill(addBillRequest);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("{billId}/pay")
    private ResponseEntity<BillResponse> payBill(@PathVariable UUID billId) {
        billService.payBill(billId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{userId}/{billId}/remove")
    private ResponseEntity<Void> removeBill(@PathVariable UUID userId, @PathVariable UUID billId) {

        userService.removeBill(billId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("{userId}/all-bills")
    private ResponseEntity<List<Bill>> getAllBills(@PathVariable UUID userId) {
        List<Bill> bills = userService.allUserBills(userId);

        return ResponseEntity.status(HttpStatus.OK).body(bills);
    }

    @GetMapping("/{billId}")
    private ResponseEntity<BillResponse> getBill(@PathVariable UUID billId) {

        Bill bill = billService.getById(billId);

        BillResponse response = DtoMapper.fromBill(bill);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
