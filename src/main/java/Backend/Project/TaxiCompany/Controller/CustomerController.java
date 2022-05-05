package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Entity.Car;
import Backend.Project.TaxiCompany.Entity.Customer;
import Backend.Project.TaxiCompany.Service.CarService;
import Backend.Project.TaxiCompany.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    private CarService carService;

    // get all customer
    @RequestMapping(path = "/customer", method = RequestMethod.GET)
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }

    //get by id
    @RequestMapping(path = "/customer/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomerId(id);
        return new ResponseEntity<Customer>(customer, new HttpHeaders(), HttpStatus.OK);
    }

    // add customer
    @RequestMapping(path = "/customer", method = RequestMethod.POST)
    public ResponseEntity<Customer>addCustomer(@RequestBody Customer customer) throws URISyntaxException {

        Customer newCustomer =  customerService.saveCustomer(customer);
        return ResponseEntity.created(new URI("/customer" + newCustomer.getId())).body(customer);
    }



    //get a customer by name
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerByName(@RequestParam(value="name") String name) {
        Customer customer = customerService.getCustomerByInfo(name);
        return new ResponseEntity<Customer>(customer, new HttpHeaders(), HttpStatus.OK);
    }



    //update
    @RequestMapping(path = "/customer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> editCustomer(@RequestBody Customer customer, @PathVariable long id){
        customerService.updateCustomer(id,customer);
        return ResponseEntity.ok().build();
    }

    //delete
    @RequestMapping(path = "/customer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable("id") long id){
        customerService.deleteCustomer(id);
    }

    //booking
    @RequestMapping(path = "/customer/{id}/booking", method = RequestMethod.POST)
    public int customerBooking(@RequestBody Customer customer, @PathVariable("id") Long id){
        return customerService.customerBooking(id,customer);


    }




}
