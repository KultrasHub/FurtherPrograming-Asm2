package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Exception.InvalidRequestException;
import Backend.Project.TaxiCompany.Exception.RecordNotFoundException;
import Backend.Project.TaxiCompany.Model.Driver;
import Backend.Project.TaxiCompany.Service.DriverService;
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



@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TaxiCompanyApplication.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DriverService service;

    String rootURI = "/drivers/";

    long id1 = 0, id2 = 1, id3 = 2, id4 = 3;
    String name1 = "Toan", name2 = "Khoa", name3 = "Ngan", name4 = "Thanh";

    Driver driver1 = new Driver(id1, ZonedDateTime.now(), name1);
    Driver driver2 = new Driver(id2, ZonedDateTime.now(), name2);
    Driver driver3 = new Driver(id3, ZonedDateTime.now(), name3);

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    public void getAllDrivers_success() throws Exception{
        List<Driver> drivers = new ArrayList<>(Arrays.asList(driver1, driver2, driver3));
        int size = drivers.size();

        Mockito.when(service.getAllDrivers()).thenReturn(drivers);

        mvc.perform(MockMvcRequestBuilders
                        .get(rootURI)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(jsonPath("$[2].name", is(name3), String.class));

    }

    @Test
    public void getDriverById_success() throws Exception{
        Mockito.when(service.getDriverById(id3)).thenReturn(driver3);

        mvc.perform(MockMvcRequestBuilders
                        .get(rootURI + id3)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(name3), String.class));
    }


    @Test
    public void createDriver_success() throws Exception {
        Driver driver = new Driver(id4, ZonedDateTime.now(), name4);

        Mockito.when(service.createDriver(any(Driver.class))).thenReturn(driver);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(driver));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(name4), String.class));
    }

    @Test
    public void updateDriver_success() throws Exception {
        Driver newDriver = new Driver(id1, ZonedDateTime.now(), name4);

        Mockito.when(service.getDriverById(driver1.getId())).thenReturn(driver1);
        Mockito.when(service.updateDriverById(eq(driver1.getId()), any(Driver.class))).thenReturn(newDriver);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newDriver));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(id1), Long.class))
                .andExpect(jsonPath("$.name", is(name4), String.class));
    }

    @Test
    public void updateDriver_nullId() throws Exception {
        Driver newDriver = new Driver(ZonedDateTime.now(), name4);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newDriver));

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result ->
                        assertEquals("Must not be null!", result.getResolvedException().getMessage()));
    }

    @Test
    public void updateDriver_recordNotFound() throws Exception {
        Driver newDriver = new Driver((long)10, ZonedDateTime.now(), name4);
        List<Driver> drivers = new ArrayList<>(Arrays.asList(driver1, driver2, driver3));

        Mockito.when(service.getAllDrivers()).thenReturn(drivers);
        Mockito.when(service.updateDriverById(eq(newDriver.getId()), any(Driver.class))).thenThrow(new RecordNotFoundException("No driver found for given ID!"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put(rootURI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newDriver));

        mvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof RecordNotFoundException))
                .andExpect(result ->
                        assertEquals("No driver found for given ID!", result.getResolvedException().getMessage()));
    }

    @Test
    public void deleteDriverById_success() throws Exception {
        List<Driver> drivers = new ArrayList<>(Arrays.asList(driver1, driver2, driver3));

        Mockito.when(service.getAllDrivers()).thenReturn(drivers);
        Mockito.when(service.getDriverById(driver2.getId())).thenReturn(driver2);

        mvc.perform(MockMvcRequestBuilders
                        .delete(rootURI + id2)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
