package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Exception.RecordNotFoundException;
import Backend.Project.TaxiCompany.Model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    //CRUD
    public List<Customer> getAllCustomers() {
        List<Customer> list = sessionFactory.getCurrentSession().createQuery("from Customer").list();
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<Customer>();
        }
    }

    public Customer getCustomerById(Long id) throws RecordNotFoundException {
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Customer C where C.id = :id")
                .setParameter("id", id)
                .list();

        if(result != null && !result.isEmpty()) {
            return (Customer) result.get(0);
        } else {
            throw new RecordNotFoundException("No Customer found for given ID");
        }
    }
    public Customer getCustomerByName(String name) throws RecordNotFoundException {
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Customer C where C.name = :name")
                .setParameter("name", name)
                .list();

        if(result != null && !result.isEmpty()) {
            return (Customer) result.get(0);
        } else {
            throw new RecordNotFoundException("No Customer found for given name");
        }
    }

    public Customer getCustomerByAddress(String address) throws RecordNotFoundException {
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Customer C where C.address = :address")
                .setParameter("address", address)
                .list();

        if(result != null && !result.isEmpty()) {
            return (Customer) result.get(0);
        } else {
            throw new RecordNotFoundException("No Customer found for given address");
        }
    }
    public Customer getCustomerByPhone(String phone) throws RecordNotFoundException {
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Customer C where C.phone = :phone")
                .setParameter("phone", phone)
                .list();

        if(result != null && !result.isEmpty()) {
            return (Customer) result.get(0);
        } else {
            throw new RecordNotFoundException("No Customer found for given phone number");
        }
    }
    public Customer createCustomer(Customer customerEntity) {
        sessionFactory.getCurrentSession().save(customerEntity);
        return customerEntity;
    }

    public Customer updateCustomerById(Long id, Customer customerEntity) {
        Session session = sessionFactory.getCurrentSession();
        List result =  session
                .createQuery("from Booking I where I.id = :id")
                .setParameter("id", id)
                .list();
        if(result != null && !result.isEmpty()) {
            Customer cus = (Customer) result.get(0);
            session.evict(cus);
            cus.setName(customerEntity.getName())
                    .setAddress(customerEntity.getAddress())
                    .setPhone((customerEntity.getPhone()))
                    .setCreatedDate(customerEntity.getCreatedDate());
            session.update(cus);
            return cus;
        } else {
            throw new RecordNotFoundException("No Customer found for given ID");
        }
    }

    public void deleteCustomerById(Long id) {
        int result = sessionFactory.getCurrentSession()
                .createQuery("delete from Customer C where C.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
    public Customer addCustomer(Customer customer)
    {
        sessionFactory.getCurrentSession().save(customer);
        return customer;
    }
}
