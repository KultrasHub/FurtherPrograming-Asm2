package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Model.Driver;
import Backend.Project.TaxiCompany.Service.BookingService;
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

import java.awt.print.Book;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TaxiCompanyApplication.class)
@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookingService service;
    String rootURI="/bookings/";
    //sample
    Customer c1=new Customer().setId(0).setName("Hornet");
    Customer c2=new Customer().setId(1).setName("Hive Knight");
    Customer c3=new Customer().setId(2).setName("Maggot");
    Driver d1=new Driver().setId(0).setName("Stag");
    Driver d2=new Driver().setId(1).setName("Beetle");
    Car car1=new Car().setId(0).setLicensePlate("12-Forgotten Cross Road");
    Car car2=new Car().setId(1).setLicensePlate("84-Ancient Basin");
    String date1 = "2022-04-09T02:17:01.0006475+07:00", date2 = "2022-05-09T02:17:01.0006475+07:00", date3 = "2022-06-09T02:17:01.0006475+07:00";
    Booking b1=new Booking().setId(0).setCustomer(c1).setDriver(d1).setCar(car1);
    Booking b2=new Booking().setId(1).setCustomer(c2).setDriver(d1).setCar(car1);
    Booking b3=new Booking().setId(2).setCustomer(c1).setDriver(d2).setCar(car1);
    Booking b4=new Booking().setId(3).setCustomer(c3).setDriver(d1).setCar(car2);
    private List<Booking> getAList() {
        return new ArrayList<Booking> (Arrays.asList(b1, b2, b3, b4));
    }
    @Test
    public void getAllBookings() throws Exception{
        List<Booking> list= getAList();
        //set get allto return the list
        Mockito.when(service.getAllBookings()).thenReturn(list);
        //
        mvc.perform(MockMvcRequestBuilders
                .get(rootURI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(list.size())))
                .andExpect(jsonPath("$[0].id",is(b1.getId()),Long.class))
                .andExpect(jsonPath("$[2].car.id",is(car1.getId()),Long.class))
                .andExpect(jsonPath("$[1].customer.name",is(c2.getName()),String.class));
    }
    @Test
    public void getBookingsByDate() throws Exception{
        List<Booking> list=getAList();
        Mockito.when(service.getBookingsByDate(any(String.class))).thenReturn(list);

        mvc.perform(MockMvcRequestBuilders
                .get(rootURI+"/date&d=1,March,2020")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(list.size())))
                .andExpect(jsonPath("$[0].id",is(b1.getId()),Long.class))
                .andExpect(jsonPath("$[2].car.id",is(car1.getId()),Long.class))
                .andExpect(jsonPath("$[1].customer.name",is(c2.getName()),String.class));
    }
    @Test
    public void getBookingById() throws Exception{
        Mockito.when(service.getBookingById((long)1)).thenReturn(b2);
        mvc.perform(MockMvcRequestBuilders
                .get(rootURI + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id",is(1)))
                .andExpect(jsonPath("customer.name",is(c2.getName()),String.class))
                .andExpect(jsonPath("driver.name",is(d1.getName()),String.class));
    }

    @Test
    public void createBooking() throws Exception{
        Mockito.when(service.createBooking(any(Booking.class))).thenReturn(b1);
        mvc.perform(MockMvcRequestBuilders.post(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(b1)))
                .andExpect(jsonPath("$.id",is(b1.getId()),Long.class))
                .andExpect(jsonPath("$.customer.id",is(c1.getId()),Long.class));

    }

    @Test
    public void getBookingInAPeriod() throws Exception {

        List<Booking> list= getAList();
        list.remove(list.size()-1);//remove the last
        Mockito.when(service.getBookingInAPeriod(ZonedDateTime.parse(date2), ZonedDateTime.parse(date3))).thenReturn(list);
        mvc.perform(MockMvcRequestBuilders.get(rootURI+"period")
                .param("start",date2)
                .param("end",date3)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(3)))//there should only 3
                .andExpect(jsonPath("$[0].id",is(b1.getId()),Long.class))
                .andExpect(jsonPath("$[1].id",is(b2.getId()),Long.class));

    }

    @Test
    public void getCarUsage() throws Exception {
        CarUsage u1=new CarUsage();
        CarUsage u2=new CarUsage();
        u1.addUsageTime(car1);
        u1.addUsageTime(car1);
        u1.addUsageTime(car1);
        //System.out.println("Usage Time is:"+u1.getUsageTime());
        u2.addUsageTime(car2);
        u2.addUsageTime(car1);//will reject
        ArrayList<CarUsage> list=new ArrayList<CarUsage>(Arrays.asList(u1,u2));
        //
        Mockito.when(service.getCarUsedInAMonth("May,1119")).thenReturn(list);
        //
        mvc.perform(MockMvcRequestBuilders.get(rootURI+"carUsage")
                .param("month","May,1119")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath( "$[0].carId",is(car1.getId()),Long.class))
                .andExpect(jsonPath("$[0].usageTime",is(3),Integer.class))
                .andExpect(jsonPath("$[1].usageTime",is(1),Integer.class));

    }

    @Test
    public void updateBooking() throws Exception {
        Mockito.when(service.updateBookingById(any(Long.class),any(Booking.class))).thenReturn(b2);
        //update with id of 1
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(rootURI+"1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(b2));
        //update with id of 2
        MockHttpServletRequestBuilder request2 = MockMvcRequestBuilders
                .put(rootURI+"2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(b2));
        mvc.perform(request)
                .andExpect(jsonPath("$.id",is(b2.getId()),Long.class))
                .andExpect(jsonPath("$.customer.id",is(b2.getCustomer().getId()),Long.class));
        mvc.perform(request2)
                .andExpect(jsonPath("$.id",is(b2.getId()),Long.class))
                .andExpect(jsonPath("$.customer.id",is(b2.getCustomer().getId()),Long.class));
    }

    @Test
    public void deleteBookingById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(rootURI + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}