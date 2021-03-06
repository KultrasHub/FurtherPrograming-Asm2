package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Exception.RecordNotFoundException;
import Backend.Project.TaxiCompany.Model.Driver;
import Backend.Project.TaxiCompany.Model.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DriverService {
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public List<Driver> getAllDrivers() {
        List<Driver> driverList = sessionFactory.getCurrentSession().createQuery("from Driver").list();
        if(driverList!=null&& driverList.size() > 0) {
            return driverList;
        } else {
            return new ArrayList<Driver>();
        }
    }

    public Driver getDriverById(Long id) throws RecordNotFoundException {
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Driver I where I.id = :id")
                .setParameter("id", id)
                .list();

        if(result != null && !result.isEmpty()) {
            return (Driver) result.get(0);
        } else {
            System.out.println("No Driver found for given Id");
            return null;
        }
    }

    public Driver createDriver(Driver driverEntity) {
        sessionFactory.getCurrentSession().save(driverEntity);
        return driverEntity;
    }

    public Driver updateDriverById(Long id, Driver driverEntity) {
        Session session = sessionFactory.getCurrentSession();
        List result =  session
                .createQuery("from Driver I where I.id = :id")
                .setParameter("id", id)
                .list();
        if(result != null && !result.isEmpty()) {
            Driver driver = (Driver) result.get(0);
            session.evict(driver);
            driver.setName(driverEntity.getName())
                    .setCreatedDate(driverEntity.getCreatedDate())
                    .setRating(driverEntity.getRating())
                    .setLicenseNumber(driverEntity.getLicenseNumber())
                    .setPhoneNumber(driverEntity.getPhoneNumber());
            session.update(driver);
            return driver;
        } else {
            System.out.println("No Driver found for given Id");
            return null;
        }
    }

    public void deleteDriverById(Long id) {
        int result = sessionFactory.getCurrentSession()
                .createQuery("delete from Driver I where I.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

}
