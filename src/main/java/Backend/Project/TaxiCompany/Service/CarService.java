package Backend.Project.TaxiCompany.Service;
import Backend.Project.TaxiCompany.Exception.RecordNotFoundException;
import Backend.Project.TaxiCompany.Model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CarService {
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}
    //
    //CRUD
    public List<Car> getAllCars() {
        List<Car> list = sessionFactory.getCurrentSession().createQuery("from Car").list();
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<Car>();
        }
    }

    public Car getCarById(Long id) throws RecordNotFoundException {
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Car C where C.id = :id")
                .setParameter("id", id)
                .list();

        if(result != null && !result.isEmpty()) {
            return (Car) result.get(0);
        } else {
            System.out.println("No Car found for given ID");
            return null;
        }
    }

    public Car createCar(Car carEntity) {
        sessionFactory.getCurrentSession().save(carEntity);
        return carEntity;
    }

    public Car updateCarById(Long id, Car carEntity) {
        Session session = sessionFactory.getCurrentSession();
        List result =  session
                .createQuery("from Car C where C.id = :id")
                .setParameter("id", id)
                .list();
        if(result != null && !result.isEmpty()) {
            Car car = (Car) result.get(0);
            session.evict(car);
            car.setLicensePlate(carEntity.getLicensePlate())
                    .setCreatedDate(carEntity.getCreatedDate())
                    .setColor(carEntity.getColor())
                    .setConvertible(carEntity.getConvertible())
                    .setMake(carEntity.getMake())
                    .setModel(carEntity.getModel())
                    .setRating(carEntity.getRating());
            session.update(car);
            return car;
        } else {
            System.out.println("No Car found for given ID");
            return null;
        }
    }

    public void deleteCarById(Long id) {
        int result = sessionFactory.getCurrentSession()
                .createQuery("delete from Car C where C.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
