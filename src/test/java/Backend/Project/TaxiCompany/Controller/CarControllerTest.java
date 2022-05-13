package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Model.Driver;
import Backend.Project.TaxiCompany.Service.BookingService;
import Backend.Project.TaxiCompany.Service.CarService;
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

import java.lang.reflect.Array;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TaxiCompanyApplication.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CarService service;
    //pro
    String rootURI="/cars/";
    //sample
    Car car1=new Car().setId(0).setLicensePlate("12-Forgotten Cross Road");
    Car car2=new Car().setId(1).setLicensePlate("84-Ancient Basin");
    Car car3=new Car().setId(1).setLicensePlate("77-CrystalPeak");
    Car car4=new Car().setId(1).setLicensePlate("88-HowlingCliff");
    public List<Car> getAList()
    {
        return new ArrayList<Car>(Arrays.asList(car1,car2,car3,car4));
    }

    @Test
    public void getAllCar() throws Exception {
        List<Car> list=getAList();
        Mockito.when(service.getAllCars()).thenReturn(list);
        //
        mvc.perform(MockMvcRequestBuilders.get(rootURI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath( "$",hasSize(list.size())))
                .andExpect(jsonPath("$[0].id",is(car1.getId()),Long.class))
                .andExpect(jsonPath("$[3].id",is(car4.getId()),Long.class));
    }

    @Test
    public void getCarById() throws Exception {
        Mockito.when(service.getCarById(any(Long.class))).thenReturn(car1);
        mvc.perform(MockMvcRequestBuilders
                .get(rootURI + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(car1.getId()),Long.class))
                .andExpect(jsonPath("$.licensePlate",is(car1.getLicensePlate()),String.class));
    }

    @Test
    public void createdCar() throws Exception {
        Mockito.when(service.createCar(any(Car.class))).thenReturn(car1);
        mvc.perform(MockMvcRequestBuilders
                .post(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(car1)))
                .andExpect(jsonPath("$.id",is(car1.getId()),Long.class))
                .andExpect(jsonPath("$.licensePlate",is(car1.getLicensePlate()),String.class));
    }

    @Test
    public void updateCar() throws Exception {
        Mockito.when(service.updateCarById(any(Long.class),any(Car.class))).thenReturn(car1);
        mvc.perform(MockMvcRequestBuilders
                .put(rootURI+"1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(car1)))
                .andExpect(jsonPath("$.id",is(car1.getId()),Long.class))
                .andExpect(jsonPath("$.licensePlate",is(car1.getLicensePlate()),String.class));
    }

    @Test
    public void deleteCarById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(rootURI + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}