package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Entity.Car;
import Backend.Project.TaxiCompany.Entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class CarService {
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}



    public List<Car> getAllCar(){
        return this.sessionFactory.getCurrentSession().createQuery("from Car").list();
    }

    // create a new car
    public Car saveCar(Car car){
        sessionFactory.getCurrentSession().save(car);
        return car;
    }

    //find car by id
    public Car getCarById(long id){
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Car c where c.id = :id")
                .setParameter("id", id)
                .list();

        return (Car) result.get(0);
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
