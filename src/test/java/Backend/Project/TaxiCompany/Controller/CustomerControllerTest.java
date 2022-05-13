package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Model.Driver;
import Backend.Project.TaxiCompany.Service.BookingService;
import Backend.Project.TaxiCompany.Service.CustomerService;
import Backend.Project.TaxiCompany.Support.CarUsage;
import Backend.Project.TaxiCompany.TaxiCompanyApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TaxiCompanyApplication.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerService service;
    String rootURI="/customer";
    //sample
    Customer c1=new Customer().setId(0).setName("Hornet");
    Customer c2=new Customer().setId(1).setName("Hive Knight");
    Customer c3=new Customer().setId(2).setName("Maggot");
    private List<Customer> getAList(){
        return new ArrayList<Customer>(Arrays.asList(c1,c2,c3));
    }
    @Test
    public void createCustomer() throws Exception {
        Mockito.when(service.createCustomer(any(Customer.class))).thenReturn(c1);
        mvc.perform(MockMvcRequestBuilders.post(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1)))
                .andExpect(jsonPath("$.id",is(c1.getId()),Long.class))
                .andExpect(jsonPath("$.name",is(c1.getName()),String.class));

    }

    @Test
    public void getAllCustomer() throws Exception {
        List<Customer> list=getAList();
        Mockito.when(service.getAllCustomers()).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders
                .get(rootURI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(list.size())))
                .andExpect(jsonPath("$[0].id",is(c1.getId()),Long.class))
                .andExpect(jsonPath("$[2].id",is(c3.getId()),Long.class));
    }

    @Test
    public void updateCustomer() throws Exception {
        Mockito.when(service.updateCustomerById(any(Long.class),any(Customer.class))).thenReturn(c1);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(rootURI+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1));
        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .put(rootURI+"/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(c1));
        mvc.perform(request)
                .andExpect(jsonPath("$.id",is(c1.getId()),Long.class))
                .andExpect(jsonPath("$.name",is(c1.getName()),String.class));
        mvc.perform(request2)
                .andExpect(jsonPath("$.id",is(c1.getId()),Long.class))
                .andExpect(jsonPath("$.name",is(c1.getName()),String.class));
    }

    @Test
    public void getCustomerById() throws Exception {
        Mockito.when(service.getCustomerById(any(Long.class))).thenReturn(c2);
        mvc.perform(MockMvcRequestBuilders
                .get(rootURI + "/"+1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(c2.getId()),Long.class))
                .andExpect(jsonPath("$.name",is(c2.getName()),String.class));
    }

    @Test
    public void getCustomerByName() throws Exception {
        Mockito.when(service.getCustomerByName(any(String.class))).thenReturn(c3);
        mvc.perform(MockMvcRequestBuilders
                        .get(rootURI + "&n=lala")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(c3.getId()),Long.class))
                .andExpect(jsonPath("$.name",is(c3.getName()),String.class));
    }

    @Test
    public void getCustomerByAddress() throws Exception {
        Mockito.when(service.getCustomerByAddress(any(String.class))).thenReturn(c1);
        mvc.perform(MockMvcRequestBuilders
                        .get(rootURI + "&a=lala")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(c1.getId()),Long.class))
                .andExpect(jsonPath("$.name",is(c1.getName()),String.class));
    }

    @Test
    public void getCustomerByPhone() throws Exception {
        Mockito.when(service.getCustomerByPhone(any(String.class))).thenReturn(c2);
        mvc.perform(MockMvcRequestBuilders
                        .get(rootURI + "&p=lala")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(c2.getId()),Long.class))
                .andExpect(jsonPath("$.name",is(c2.getName()),String.class));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(rootURI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}