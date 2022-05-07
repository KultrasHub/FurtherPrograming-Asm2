package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Model.Customer;
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
        return customerService.createCustomer(customer);
    }

    // get all customer
    @RequestMapping(path = "/customer", method = RequestMethod.GET)
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomers();
    }

    @RequestMapping(path = "/customer/id/{id}", method = RequestMethod.GET)
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

    @RequestMapping(path = "/deleteCustomer/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable("id") long id){
        customerService.deleteCustomerById(id);
    }




}
