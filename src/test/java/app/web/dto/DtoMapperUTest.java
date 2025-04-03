package app.web.dto;

import app.Web.dto.BillResponse;
import app.Web.mapper.DtoMapper;
import app.model.Bill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static app.web.TestBuilder.randomBill;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DtoMapperUTest {
    @Test
    void successfullyMappedBillResponseFromBill() {
        Bill bill = randomBill();

        BillResponse billResponse = DtoMapper.fromBill(bill);

        assertEquals(bill.getId(), billResponse.getId());
        assertEquals(bill.getBillNumber(), billResponse.getBillNumber());
        assertEquals(bill.getBillType(), billResponse.getBillType());
        assertEquals(bill.getStartPeriod(), billResponse.getStartPeriod());
        assertEquals(bill.getEndPeriod(), billResponse.getEndPeriod());
        assertEquals(bill.getAmount(), billResponse.getAmount());
        assertEquals(bill.getPaidOn(), billResponse.getPaidOn());
        assertEquals(bill.isPaid(), billResponse.isPaid());
    }
}
