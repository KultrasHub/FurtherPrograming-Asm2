package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Model.Driver;
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
public class DriverServiceTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    DriverService service;
    @Test
    public void getAllDrivers() {
        List<Driver> oldList=service.getAllDrivers();
        List<Driver> list=sessionFactory.getCurrentSession().createQuery("from Driver").list();
        if(list==null||list.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        assertEquals(oldList,list);//

    }

    @Test
    public void getDriverById() {
        Driver d1=service.getDriverById((long)1);
        Driver d2=service.getDriverById((long)2);
        List<Driver> list=sessionFactory.getCurrentSession().createQuery("from Driver").list();
        if(list==null||list.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        if(d1!=null)
        {
            assertEquals(list.get(0),d1);//the first id should be at the first list
            assertFalse(d1.getId()==2);
        }
        if(d2!=null)
        {
            assertEquals(list.get(1),d2);//the second id should be at the second list
            assertTrue(2==d2.getId());
        }
    }

    @Test
    public void createDriver() {
        List<Driver> before=sessionFactory.getCurrentSession().createQuery("from Driver").list();
        Driver d=new Driver().setName("gofy");
        service.createDriver(d);
        List<Driver> after=sessionFactory.getCurrentSession().createQuery("from Driver").list();
        //assert
        assertEquals(after.size(),before.size()+1);//size has increased
        assertEquals(after.get(after.size()-1),d);

    }

    @Test
    public void updateDriverById() {
        List<Driver> before=sessionFactory.getCurrentSession().createQuery("from Driver").list();
        Driver d=new Driver().setName("gofy");
        String oldName=before.get(0).getName();//-> id 1
        long oldId=before.get(0).getId();
        //update
        service.updateDriverById(oldId,d);
        List<Driver> after=sessionFactory.getCurrentSession().createQuery("from Driver").list();
        //assert
        assertEquals(before,after);//no thing should change
        assertEquals(d.getName(),after.get(0).getName());//check is the name is same
        assertNotEquals(d.getName(),oldName);//name has change
    }

    @Test
    public void deleteDriverById() {
        List<Driver> before=sessionFactory.getCurrentSession().createQuery("from Driver").list();
        if(before==null||before.isEmpty())
        {
            System.out.println("You MUST add a driver to database to test delete");
            return;
        }
        service.deleteDriverById((long)1);
        List<Driver> after=sessionFactory.getCurrentSession().createQuery("from Driver").list();
        //assert
        assertEquals(after.size()+1,before.size());//saide is reduced by1
        assertNotEquals(after.get(0),before.get(0));
        assertNull(service.getDriverById((long)1));//id 1 has been deleter
    }

}