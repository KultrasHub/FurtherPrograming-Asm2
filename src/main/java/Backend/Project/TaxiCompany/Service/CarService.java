package Backend.Project.TaxiCompany.Service;
import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Model.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}
    public void saveCar(Car car){
        sessionFactory.getCurrentSession().save(car);
    }
    public List<Car> getAllCar(){
        return this.sessionFactory.getCurrentSession().createQuery("from Car").list();
    }

    // create a new car
    public Car addCar(Car car){sessionFactory.getCurrentSession().persist(car);
        return addCar(car);
    }

    //find car by id
    public Car getCarById(long id){
        Session session = this.sessionFactory.getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, new Long(id));
        return getCarById(id);
    }

    //edit a car
    public void updateCar(long id, Car newEditCar){
        Session session = this.sessionFactory.getCurrentSession();
        Car car = (Car) session.load(Car.class, new Long(id));
        car.setLicensePlate(newEditCar.getLicensePlate());
        saveCar(car);
    }

    //delete cars
    public void deleteCar(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Car car = (Car) session.load(Car.class, new Long(id));
        if (null != car) {
            session.delete(car);
        }

    }

}
