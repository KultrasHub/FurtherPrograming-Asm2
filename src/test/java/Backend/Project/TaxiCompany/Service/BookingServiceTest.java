package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Config.DateTimeFormatConfiguration;
import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Model.Driver;
import Backend.Project.TaxiCompany.Support.CarUsage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookingServiceTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    BookingService service;

    @Test
    public void getAllBookings() {
        List<Booking> bookings=service.getAllBookings();
        List<Booking> original=sessionFactory.getCurrentSession().createQuery("from Booking").list();
        //a random book to compare with Booking
        ArrayList<Booking> failedList=new ArrayList<Booking>();
        failedList.add(new Booking());
        //assert
        assertEquals(bookings,original);
        assertNotEquals(bookings,failedList);
        //remove a component from original
        if(!original.isEmpty()) {
            original.remove(0);
            assertNotEquals(bookings,original);
        }

    }

    @Test
    public void getBookingById() {
        Booking booking1=service.getBookingById((long)1);
        Booking booking2=service.getBookingById((long)2);
        List<Booking> original=sessionFactory.getCurrentSession().createQuery("from Booking").list();
        if(original==null)
        {
            System.out.println("Test cannot be excuted due too emmpty booking in Database");
            return;
        }
        if(booking1!=null)
        {
            assertEquals(booking1,original.get(0));
        }
        if(booking2!=null)
        {
            assertEquals(booking2,original.get(1));
            assertNotEquals(booking2,original.get(0));
            assertTrue(booking2.getId()==2);//this is not absolute true in case a booking is deleted from database
            assertFalse(booking2.getId()!=2);
        }
    }

    @Test
    public void createBooking() {
        Booking nBook=new Booking();
        List<Booking> original=sessionFactory.getCurrentSession().createQuery("from Booking").list();
        service.createBooking(nBook);
        List<Booking> original2=sessionFactory.getCurrentSession().createQuery("from Booking").list();
        //assert
        assertEquals(original2.get(original2.size()-1),nBook);
        assertTrue(original2.size()-original.size()==1);//list has increase by 1
        assertNotEquals(original,original2);//there are more in list2
        assertFalse(original2.size()==original.size());
    }

    @Test
    public void updateBookingById() {
        List<Booking> original=sessionFactory.getCurrentSession().createQuery("from Booking").list();
        if(original==null||original.isEmpty())
        {
            System.out.println("For this testing to work, please add any Booking to the database");
            System.out.println("Otherwise it will throw an exception for not found the id it needed");
        }
        Customer cus=new Customer().setAddress("Twin Tower").setName("Frey");
        Car car=new Car().setLicensePlate("123-North");
        Driver driver=new Driver().setName("Hop");
        Booking nBook=new Booking().setCar(car).setCustomer(cus).setDriver(driver);
        Booking nBook2=new Booking().setCar(car).setCustomer(cus).setDriver(driver);
        //update to the first Booking
        service.updateBookingById((long)1,nBook);
        List<Booking> after=sessionFactory.getCurrentSession().createQuery("from Booking").list();
        assertTrue(original.size()==after.size());//no thing change about this
        assertEquals(original,after);//there are still equal as they check component by id and no id has changed
        assertEquals("Twin Tower",after.get(0).getCustomer().getAddress());//compare the address that is put in
        assertNotEquals(nBook2,after.get(0));//NBook2 has the same attribute but not id and saved in the database
        assertEquals(car,after.get(0).getCar());
    }

    @Test
    public void deleteBookingById() {
        List<Booking> original=sessionFactory.getCurrentSession().createQuery("from Booking").list();
        if(original==null||original.isEmpty())
        {
            System.out.println("You MUST add any booking to database to test delete");
            return;
        }
        service.deleteBookingById((long)1);
        List<Booking> after=sessionFactory.getCurrentSession().createQuery("from Booking").list();
//        System.out.println("testing");
//        for(int i=0;i<after.size();i++)
//        {
//            System.out.print("-"+after.get(i).getId());
//        }
        //assertNotEquals(original,after);//things changed
        assertTrue(original.size()-1==after.size());//after is 1 component less than
        assertFalse(after.get(0).getId()==1);//the id of 1 has been deleted
        assertEquals(original.get(1),after.get(0));//the second element has been move up
    }

    @Test
    public void getBookingInAPeriod() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Testing subjects should be Isolated by date from the current Objects in database");
        System.out.println("Testing subjects has created time from 1119-03-27 10:15:30 am -05:00 to 1119-05-30 10:15:30 am -05:00");
        System.out.println("--------------------------------------------------------------------------------");
        Booking b1=new Booking().setCreatedDate(DateTimeFormatConfiguration.String2Time("28,March,1119") );
        Booking b2=new Booking().setCreatedDate(DateTimeFormatConfiguration.String2Time("29,March,1119") );
        Booking b3=new Booking().setCreatedDate(DateTimeFormatConfiguration.String2Time("30,March,1119") );
        Booking b4=new Booking().setCreatedDate(DateTimeFormatConfiguration.String2Time("30,May,1119") );
        //add to test database
        service.createBooking(b1);
        service.createBooking(b2);
        service.createBooking(b3);
        service.createBooking(b4);
        //time
        ZonedDateTime start=DateTimeFormatConfiguration.String2Time("27,March,1119 ");
        ZonedDateTime end=DateTimeFormatConfiguration.String2Time("31,March,1119");
        //Extract
        List<Booking> list= service.getBookingInAPeriod(start,end);
        assertEquals(3,list.size());//there should only 3 booking in the period
        for(int i=0;i<list.size();i++)
        {
            //all booking in the list must not be b4
            assertNotEquals(list.get(i),b4);
        }
        //in list should be these bookings in this order
        assertEquals(list.get(0),b1);
        assertEquals(list.get(1),b2);
        assertEquals(list.get(2),b3);

    }

    @Test
    public void getCarUsedInAMonth() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Testing subjects should be Isolated by date from the current Objects in database");
        System.out.println("Testing subjects has created time from 1119-03-27 10:15:30 am -05:00 to 1119-05-30 10:15:30 am -05:00");
        System.out.println("--------------------------------------------------------------------------------");
        Car car1=new Car().setLicensePlate("1");
        Car car2=new Car().setLicensePlate("2");
        Car car3=new Car().setLicensePlate("3");
        //2 usage for car 1, 2 for car2 and 1 for car 3
        Booking b1=new Booking().setCar(car1).setCreatedDate(DateTimeFormatConfiguration.String2Time("28,March,1119") );
        Booking b2=new Booking().setCar(car1).setCreatedDate(DateTimeFormatConfiguration.String2Time("27,March,1119") );
        Booking b3=new Booking().setCar(car2).setCreatedDate(DateTimeFormatConfiguration.String2Time("26,March,1119") );
        Booking b4=new Booking().setCar(car2) .setCreatedDate(DateTimeFormatConfiguration.String2Time("28,May,1119") );
        Booking b5=new Booking().setCar(car3) .setCreatedDate(DateTimeFormatConfiguration.String2Time("28,May,1119") );
        service.createBooking(b1);
        service.createBooking(b2);
        service.createBooking(b3);
        service.createBooking(b4);
        service.createBooking(b5);
        //time
        List<CarUsage> list=service.getCarUsedInAMonth("March,1119");
        assertNotNull(list);//should find the given above
        assertEquals(2,list.size());//should only 2 cars that march
        assertEquals(2,list.get(0).usageTime);//for the first car there are 2 usage in the given time
        assertNotEquals(2,list.get(1).usageTime);//only 1 in 2 usage are in the given time
        assertEquals(car1.getId(),list.get(0).carId);//in the correct order car1 should be first
        for(int i=0;i<list.size();i++)
        {
            assertNotEquals(car3.getLicensePlate(),list.get(i).getLicense());//car3 should not be in the list
        }
    }

}