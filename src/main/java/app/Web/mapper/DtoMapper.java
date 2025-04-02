package app.Web.mapper;

import app.Web.dto.BillResponse;
import app.model.Bill;
import lombok.experimental.UtilityClass;



@UtilityClass
public class DtoMapper {
    public static BillResponse fromBill(Bill entity) {
        return BillResponse.builder()
                .id(entity.getId())
                .billNumber(entity.getBillNumber())
                .billType(entity.getBillType())
                .amount(entity.getAmount())
                .startPeriod(entity.getStartPeriod())
                .endPeriod(entity.getEndPeriod())
                .isPaid(entity.isPaid())
                .paidOn(entity.getPaidOn())
                .build();
    }

}
