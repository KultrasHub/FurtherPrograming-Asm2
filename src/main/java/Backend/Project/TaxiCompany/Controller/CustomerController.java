package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Entity.Customer;
import Backend.Project.TaxiCompany.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    // add customer
    @RequestMapping(path = "/addCustomer", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }

    // get all customer
    @RequestMapping(path = "/customer", method = RequestMethod.GET)
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }

    //get a customer by name
    @RequestMapping(path = "/customer/{name}", method = RequestMethod.GET)
    public Customer getCustomerByName(@PathVariable String name){
        return customerService.getCustomerByName(name);
    }

    //get a customer by address
    @RequestMapping(path = "/customer/{address}", method = RequestMethod.GET)
    public Customer getCustomerByAddress(@PathVariable String address){
        return customerService.getCustomerByAddress(address);
    }

    //get a customer by phone
    @RequestMapping(path = "/customer/{phone}", method = RequestMethod.GET)
    public Customer getCustomerByPhone(@PathVariable String phone){
        return customerService.getCustomerByAddress(phone);
    }

    @RequestMapping(path = "/deleteCustomer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable("id") long id){
        customerService.deleteCustomer(id);
    }




}
