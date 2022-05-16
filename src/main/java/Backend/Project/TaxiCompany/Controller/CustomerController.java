package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Exception.InvalidRequestException;
import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // add customer
    @RequestMapping(path = "/customer", method = RequestMethod.POST)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) throws Exception{
        if(customer == null) {
            throw new InvalidRequestException("Must not be null!");
        }
        Customer c= customerService.createCustomer(customer);
        return new ResponseEntity<Customer>(c,new HttpHeaders(), HttpStatus.OK);
    }

    // get all customer
    @RequestMapping(path = "/customer", method = RequestMethod.GET)
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }
    //Update Customer
    @RequestMapping(path = "/customer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer cus, @PathVariable long id) throws Exception{
        if(cus == null) {
            throw new InvalidRequestException("Must not be null!");
        }
        Customer c= customerService.updateCustomerById(id,cus);
        return new ResponseEntity<Customer>(c,new HttpHeaders(), HttpStatus.OK);
    }
    @RequestMapping(path = "/customer/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return customer;
    }

    //get a customer by name
    @RequestMapping(path = "/customer&n={name}", method = RequestMethod.GET)
    public Customer getCustomerByName(@PathVariable("name") String name){
        return customerService.getCustomerByName(name);
    }

    //get a customer by address
    @RequestMapping(path = "/customer&a={address}", method = RequestMethod.GET)
    public Customer getCustomerByAddress(@PathVariable("address") String address){
        return customerService.getCustomerByAddress(address);
    }

    //get a customer by phone
    @RequestMapping(path = "/customer&p={phone}", method = RequestMethod.GET)
    public Customer getCustomerByPhone(@PathVariable String phone){
        return customerService.getCustomerByPhone(phone);
    }

    @RequestMapping(path = "/customer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable("id") long id){
        customerService.deleteCustomerById(id);
    }




}
