package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Exception.*;
import Backend.Project.TaxiCompany.Model.*;
import Backend.Project.TaxiCompany.Service.InvoiceService;
import Backend.Project.TaxiCompany.TaxiCompanyApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
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
import static java.net.URLEncoder.encode;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes= TaxiCompanyApplication.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private InvoiceService service;

    final static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    String rootURI = "/invoices/";
    long id1 = 0, id2 = 1, id3 = 2, id4 = 3;
    float revenue1 = 111, revenue2 = 222, revenue3 = 333, revenue4 = 444;
    String date1 = "2022-04-09T02:17:01.0006475+07:00", date2 = "2022-05-09T02:17:01.0006475+07:00", date3 = "2022-06-09T02:17:01.0006475+07:00";


    Invoice invoice1 = new Invoice(id1, ZonedDateTime.parse(date1), revenue1);
    Invoice invoice2 = new Invoice(id2, ZonedDateTime.parse(date2), revenue2);
    Invoice invoice3 = new Invoice(id3, ZonedDateTime.parse(date3), revenue3);

    Customer customer1 = new Customer(id1);
    Customer customer2 = new Customer(id2);

    Driver driver1 = new Driver(id1);
    Driver driver2 = new Driver(id2);

    Car car1 = new Car(id1);
    Car car2 = new Car(id2);
    Car car3 = new Car(id3);

    Booking booking1 = new Booking(id1, ZonedDateTime.now(), customer1, driver1, car1, invoice1);
    Booking booking2 = new Booking(id2, ZonedDateTime.now(), customer1, driver2, car2, invoice2);
    Booking booking3 = new Booking(id3, ZonedDateTime.now(), customer2, driver2, car3, invoice3);

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
                .andExpect(jsonPath("$[2].revenue", is(revenue3), Float.class));
    }

    @Test
    public void getInvoicesWithinPeriod_success() throws Exception {
        List<Invoice> invoiceList1 = new ArrayList<>(Arrays.asList(invoice1, invoice2, invoice3));
        List<Invoice> invoiceList2 = new ArrayList<>(Arrays.asList(invoice2, invoice3));
        int size1 = invoiceList1.size();
        int size2 = invoiceList2.size();

        Mockito.when(service.getAllInvoices()).thenReturn(invoiceList1);
        Mockito.when(service.listInvoiceBetween(ZonedDateTime.parse(date2), ZonedDateTime.parse(date3))).thenReturn(invoiceList2);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(rootURI)
                .param("start", date2)
                .param("end", date3 )
                .contentType(MediaType.APPLICATION_JSON);


        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size2)))
                .andExpect(jsonPath("$[0].revenue", is(invoice2.getRevenue()), Float.class))
                .andExpect(jsonPath("$[1].revenue", is(invoice3.getRevenue()), Float.class));
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
    public void getInvoicesByACustomerInAPeriod() throws Exception{
        Mockito
                .when(service.getInvoicesByACustomerInAPeriod(customer1.getId(), ZonedDateTime.parse(date1), ZonedDateTime.parse(date3), 0, 9))
                .thenReturn(Arrays.asList(invoice1, invoice2));
        Mockito
                .when(service.getInvoicesByACustomerInAPeriod(customer1.getId(), ZonedDateTime.parse(date2), ZonedDateTime.parse(date3), 0, 9))
                .thenReturn((Arrays.asList(invoice2)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(rootURI + "c")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(customer1.getId()))
                .param("start", date1)
                .param("end", date3);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].revenue", is(invoice1.getRevenue()), Float.class))
                .andExpect(jsonPath("$[1].revenue", is(invoice2.getRevenue()), Float.class));

        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .get(rootURI + "c")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(customer1.getId()))
                .param("start", date2)
                .param("end", date3);

        mvc.perform(request2)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].revenue", is(invoice2.getRevenue()), Float.class));

    }

    @Test
    public void getInvoicesByADriverInAPeriod() throws Exception{
        Mockito
                .when(service.getInvoicesByADriverInAPeriod(driver1.getId(), ZonedDateTime.parse(date1), ZonedDateTime.parse(date3), 0, 9))
                .thenReturn(Arrays.asList(invoice1, invoice2));
        Mockito
                .when(service.getInvoicesByADriverInAPeriod(driver1.getId(), ZonedDateTime.parse(date1), ZonedDateTime.parse(date2), 0, 9))
                .thenReturn((Arrays.asList(invoice2)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(rootURI + "d")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(driver1.getId()))
                .param("start", date1)
                .param("end", date3);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].revenue", is(invoice1.getRevenue()), Float.class))
                .andExpect(jsonPath("$[1].revenue", is(invoice2.getRevenue()), Float.class));

        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .get(rootURI + "d")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(driver1.getId()))
                .param("start", date1)
                .param("end", date2);

        mvc.perform(request2)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].revenue", is(invoice2.getRevenue()), Float.class));
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
                .content(mapper.writeValueAsString(invoice));

        mvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.revenue", is(revenue4), Float.class));
    }

    @Test
    public void updateInvoice_success() throws Exception{
        Invoice newInvoice = new Invoice(id1, ZonedDateTime.now(), revenue4);

        Mockito.when(service.getInvoiceById(invoice1.getId())).thenReturn(invoice1);
        Mockito.when(service.updateInvoice(eq(invoice1.getId()), any(Invoice.class))).thenReturn(newInvoice);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newInvoice));

        mvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(id1), Long.class))
                .andExpect(jsonPath("$.revenue", is(revenue4), Float.class));
    }

    @Test
    public void updateInvoice_nullId() throws Exception{
        Invoice newInvoice = new Invoice(ZonedDateTime.now(), revenue4);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newInvoice));

        mvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result ->
                        assertEquals("Must not be null!", result.getResolvedException().getMessage()));
    }

    @Test
    public void updateInvoice_recordNotFound() throws Exception {
        Invoice newInvoice = new Invoice((long)10, ZonedDateTime.now(), revenue4);
        List<Invoice> invoices = new ArrayList<>(Arrays.asList(invoice1, invoice2, invoice3));

        Mockito.when(service.getAllInvoices()).thenReturn(invoices);
        Mockito.when(service.updateInvoice(eq(newInvoice.getId()), any(Invoice.class))).thenThrow(new RecordNotFoundException("No invoice found for given ID!"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newInvoice));

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

        Mockito.when(service.getAllInvoices()).thenReturn(invoices);
        Mockito.when(service.getInvoiceById(invoice2.getId())).thenReturn(invoice2);

        mvc.perform(MockMvcRequestBuilders
                        .delete(rootURI + invoice2.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
