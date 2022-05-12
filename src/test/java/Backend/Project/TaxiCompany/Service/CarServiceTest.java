package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Model.Car;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CarServiceTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    CarService service;

    @Test
    public void getAllCars() {
        List<Car> list= service.getAllCars();
        List<Car> original=sessionFactory.getCurrentSession().createQuery("from Car").list();
        //assert
        assertEquals(list,original);//they should be the same
        if(!original.isEmpty())
        {
            original.remove(0);
            assertNotEquals(list,original);//changed
        }
    }

    @Test
    public void getCarById() {
        Car car1=service.getCarById((long)1);
        Car car2=service.getCarById((long)2);
        List<Car> original=sessionFactory.getCurrentSession().createQuery("from Car").list();
        if(original==null)
        {
            System.out.println("Test cannot be excuted due too emmpty cars in Database");
            return;
        }
        if(car1!=null)
        {
            assertEquals(car1,original.get(0));
        }
        if(car2!=null)
        {
            assertEquals(car2,original.get(1));//should be similar
            assertEquals(car1,original.get(1));
            assertTrue(car1.getId()==1);//not always correct
        }
    }

    @Test
    public void createCar() {
        List<Car> before=sessionFactory.getCurrentSession().createQuery("from Car").list();
        Car car =new Car();
        service.createCar(car);
        List<Car> after=sessionFactory.getCurrentSession().createQuery("from Car").list();
         //assert
        assertEquals(after.size()-1,before.size()); //after should be larger than before
        assertEquals(after.get(after.size()-1),car);//last in after should be car
        assertNotEquals(before,after);//there have different elements now
    }

    @Test
    public void updateCarById() {
        Car car2 =new Car().setLicensePlate("Sai-124");
        List<Car> before=sessionFactory.getCurrentSession().createQuery("from Car").list();
        if(before==null||before.isEmpty())
        {
            System.out.println("-------------------------------------------------------");
            System.out.println("Make sure there are element in database for this testing");
            System.out.println("-------------------------------------------------------");
            return;
        }
        long id= 1;
        service.updateCarById(id,car2);
        List<Car> after=sessionFactory.getCurrentSession().createQuery("from Car").list();

        assertTrue(after.size()==before.size());//nothing change on size
        assertEquals(before,after);//before and after still share the same id
        assertNotEquals("Sai-123",after.get(after.size()-1).getLicensePlate());//should not be equal to the old car
        assertEquals(id,after.get(0).getId());


    }

    @Test
    public void deleteCarById() {
        List<Car> original=sessionFactory.getCurrentSession().createQuery("from Car").list();
        if(original==null||original.isEmpty())
        {
            System.out.println("You MUST add any car to database to test delete");
            return;
        }
        service.deleteCarById((long)1);//delete the first
        List<Car> after=sessionFactory.getCurrentSession().createQuery("from Car").list();
        assertEquals(1,original.size()-after.size());//original is larger than after by 1
        assertNull(service.getCarById((long)1));//car of id 1 is already deleted
        assertNotEquals(original.get(0),after.get(0));//they are not equal
    }
}