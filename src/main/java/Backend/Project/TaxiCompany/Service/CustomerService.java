package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Entity.Customer;
import Backend.Project.TaxiCompany.Entity.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    //add  customer
    @Transactional
    public Customer addCustomer(Customer customer){sessionFactory.getCurrentSession().persist(customer);
        return addCustomer(customer);
    }

    //getAll customer
    @Transactional
    public List<Customer> getAllCustomers(){
        return this.sessionFactory.getCurrentSession().createQuery("from Customer").list();
    }

    //getCustomer by name
    @Transactional
    public Customer getCustomerByName(String name){
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, new String(name));
        return getCustomerByName(name);
    }

    @Transactional
    public Customer getCustomerByAddress(String Address){
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, new String(Address));
        return getCustomerByAddress(Address);
    }

    @Transactional
    public Customer getCustomerByPhone(String phone){
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, new String(phone));
        return getCustomerByPhone(phone);
    }

    //deleteCustomer
    @Transactional
    public void deleteCustomer(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, new Long(id));
        if (null != customer) {
            session.delete(customer);
        }

    }


}
