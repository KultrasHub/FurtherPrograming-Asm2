package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Entity.Booking;
import Backend.Project.TaxiCompany.Entity.Car;
import Backend.Project.TaxiCompany.Entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private SessionFactory sessionFactory;
    private CarService carService;

//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    //getAll customer

    public List<Customer> getAllCustomers(){
        return this.sessionFactory.getCurrentSession().createQuery("from Customer AS c").list();
    }
    //get by id
    public Customer getCustomerId(Long id)  {
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Customer c where c.id = :id")
                .setParameter("id", id)
                .list();

            return (Customer) result.get(0);
    }
    //add  customer

    public Customer saveCustomer(Customer customer){
        sessionFactory.getCurrentSession().save(customer);
        return customer;
    }



    //getCustomer by name

    public Customer getCustomerByInfo(String name){
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, new String(name));
        return getCustomerByInfo(name);
    }

    //edit a customer
    public void updateCustomer(long id, Customer newEditCustomer){
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, new Long(id));
        customer.setName(newEditCustomer.getName());
        customer.setAddress(newEditCustomer.getAddress());
        customer.setPhone(newEditCustomer.getPhone());

        saveCustomer(customer);
    }

    //deleteCustomer

    public void deleteCustomer(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, new Long(id));
        if (null != customer) {
            session.delete(customer);
        }

    }


    //Customer booking

    public int customerBooking(Long id, Customer customer){
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Customer c where c.id = :id")
                .setParameter("id", id)
                .list();
         result.get(0);

        for (Booking booking: customer.getBooking()){
            booking.setCustomer(customer);
        }
        saveCustomer(customer);
//        return (Customer) result.get(0);
        return (int) customer.getId();

    }


    //Additional API
//    public List<Customer> revenueByCustomer(ZonedDateTime start, ZonedDateTime end){
//
//    }

}
