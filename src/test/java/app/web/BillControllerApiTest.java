package app.web;

import app.Web.BillController;
import app.Web.dto.AddBillRequest;
import app.model.Bill;
import app.model.BillType;
import app.service.BillService;
import app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static app.web.TestBuilder.randomBill;
import static app.web.TestBuilder.randomUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillController.class)
public class BillControllerApiTest {
    @MockitoBean
    private BillService billService;
    @MockitoBean
    private UserService userService;
    @Autowired
    private MockMvc mvc;

    @Test
    void addBill_success() throws Exception {

        AddBillRequest addBillRequest = AddBillRequest.builder()
                .userId(UUID.randomUUID())
                .billType(BillType.ELECTRICITY)
                .billNumber("2423423423")
                .build();

        when(billService.createBill(addBillRequest)).thenReturn(randomBill());
        when(userService.checkForUser(addBillRequest)).thenReturn(randomUser());
        MockHttpServletRequestBuilder request = post("/api/v1/bills/add-bill")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(addBillRequest));

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void payBill_success() throws Exception {
        MockHttpServletRequestBuilder request = put("/api/v1/bills/{billId}/pay", UUID.randomUUID());

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void removeBillFromUserBills_success() throws Exception {
        MockHttpServletRequestBuilder request = delete("/api/v1/bills/{userId}/{billId}/remove", UUID.randomUUID(), UUID.randomUUID());

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void getAllUserBills_success() throws Exception {
        List<Bill> allBills = List.of(randomBill());
        when(userService.allUserBills(any())).thenReturn(allBills);

        MockHttpServletRequestBuilder request = get("/api/v1/bills/{userId}/all-bills", UUID.randomUUID());

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void getBillById_success() throws Exception {
        when(billService.getById(any())).thenReturn(randomBill());

        MockHttpServletRequestBuilder request = get("/api/v1/bills/{billId}", UUID.randomUUID());

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("billNumber").isNotEmpty())
                .andExpect(jsonPath("billType").isNotEmpty())
                .andExpect(jsonPath("amount").isNotEmpty())
                .andExpect(jsonPath("startPeriod").isNotEmpty())
                .andExpect(jsonPath("endPeriod").isNotEmpty());
    }
}
