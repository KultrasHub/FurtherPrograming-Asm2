package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Exception.*;
import Backend.Project.TaxiCompany.Model.Invoice;
import Backend.Project.TaxiCompany.Service.InvoiceService;
import Backend.Project.TaxiCompany.TaxiCompanyApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes= TaxiCompanyApplication.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private InvoiceService service;

    String rootURI = "/invoices/";
    long id1 = 0, id2 = 1, id3 = 2, id4 = 3;
    float revenue1 = 111, revenue2 = 222, revenue3 = 333, revenue4 = 444;

    Invoice invoice1 = new Invoice(id1, ZonedDateTime.now(), revenue1);
    Invoice invoice2 = new Invoice(id2, ZonedDateTime.now(), revenue2);
    Invoice invoice3 = new Invoice(id3, ZonedDateTime.now(), revenue3);


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getAllInvoices_success() throws Exception{
        List<Invoice> invoices = new ArrayList<>(Arrays.asList(invoice1, invoice2, invoice3));
        int size = invoices.size();

        Mockito.when(service.getAllInvoices()).thenReturn(invoices);

        mvc.perform(MockMvcRequestBuilders
                        .get(rootURI)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(jsonPath("$[" + id3 + "].revenue", is(revenue3), Float.class));
    }

    @Test
    public void getInvoiceById_success() throws Exception{
        Mockito.when(service.getInvoiceById(id3)).thenReturn(invoice3);

        mvc.perform(MockMvcRequestBuilders
                        .get(rootURI + id3)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("revenue", is(revenue3), Float.class));
    }

    @Test
    public void getInvoicesByACustomerInAPeriod() {
    }

    @Test
    public void getInvoicesByADriverInAPeriod() {
    }

    @Test
    public void createInvoice_success() throws Exception {
        ZonedDateTime time = ZonedDateTime.now();
        Invoice invoice = new Invoice(id4, time, revenue4);

        Mockito.when(service.createInvoice(any(Invoice.class))).thenReturn(invoice);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(invoice));

        mvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.revenue", is(revenue4), Float.class));
    }

    @Test
    public void updateInvoiceById_success() throws Exception{
        Invoice newInvoice = new Invoice(id1, ZonedDateTime.now(), revenue4);

        Mockito.when(service.getInvoiceById(invoice1.getId())).thenReturn(invoice1);
        Mockito.when(service.updateInvoice(eq(invoice1.getId()), any(Invoice.class))).thenReturn(newInvoice);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newInvoice));

        mvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(id1), Long.class))
                .andExpect(jsonPath("$.revenue", is(revenue4), Float.class));
    }

    @Test
    public void updateInvoiceById_nullId() throws Exception{
        Invoice newInvoice = new Invoice(ZonedDateTime.now(), revenue4);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newInvoice));

        mvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result ->
                        assertEquals("Must not be null!", result.getResolvedException().getMessage()));
    }

    @Test
    public void updateInvoiceById_recordNotFound() throws Exception {
        Invoice newInvoice = new Invoice((long)10, ZonedDateTime.now(), revenue4);

        Mockito.when((service.updateInvoice(eq(newInvoice.getId()), any(Invoice.class)))).thenThrow(new RecordNotFoundException("No invoice found for given ID!"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newInvoice));

        mvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result ->
                        assertEquals("No invoice found for given ID!", result.getResolvedException().getMessage()));
    }

    @Test
    public void deleteInvoiceById_success() throws Exception{
        List<Invoice> invoices = new ArrayList<>(Arrays.asList(invoice1, invoice2, invoice3));
        int size = invoices.size();
        Mockito.when(service.getAllInvoices()).thenReturn(invoices);
        Mockito.when(service.getInvoiceById(invoice2.getId())).thenReturn(invoice2);

        mvc.perform(MockMvcRequestBuilders
                        .delete(rootURI + invoice2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
