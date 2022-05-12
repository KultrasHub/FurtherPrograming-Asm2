package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Config.DateTimeFormatConfiguration;
import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Model.Driver;
import Backend.Project.TaxiCompany.Model.Invoice;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InvoiceServiceTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    InvoiceService service;
    @Test
    public void getAllInvoices() {
        List<Invoice> before=service.getAllInvoices();
        List<Invoice> after=sessionFactory.getCurrentSession().createQuery("from Invoice").list();
        if(before==null||before.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        assertEquals(before,after);
    }

    @Test
    public void getInvoiceById() {
        Invoice i1=service.getInvoiceById((long)1);
        Invoice i2=service.getInvoiceById((long)2);
        List<Invoice> before=sessionFactory.getCurrentSession().createQuery("from Invoice").list();
        if(i1!=null)
        {
            assertEquals(i1,before.get(0));
            assertEquals(1,i1.getId());
        }
        if(i2!=null)
        {
            assertEquals(i2,before.get(1));
            assertEquals(2,i2.getId());//correct id
            assertNotEquals(i1,before.get(1));
        }
    }

    @Test
    public void getInvoicesByACustomerInAPeriod() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Testing subjects should be Isolated by date from the current Objects in database");
        System.out.println("Testing subjects has created time from 24,March,1119 to 30, March,1119");
        System.out.println("--------------------------------------------------------------------------------");
        Customer cus1=new Customer();
        Customer cus2=new Customer();
        Booking b1=new Booking().setCustomer(cus1);
        Booking b2=new Booking().setCustomer(cus2);
        Invoice c1=new Invoice().setBooking(b1).setCreatedDate(DateTimeFormatConfiguration.String2Time("28,March,1119") );
        Invoice c2=new Invoice().setBooking(b1).setCreatedDate(DateTimeFormatConfiguration.String2Time("26,March,1119") );
        Invoice c3=new Invoice().setBooking(b2).setCreatedDate(DateTimeFormatConfiguration.String2Time("25,March,1119") );
        Invoice c4=new Invoice().setBooking(b1).setCreatedDate(DateTimeFormatConfiguration.String2Time("24,May,1119") );
        //add
        service.createInvoice(c1);
        service.createInvoice(c2);
        service.createInvoice(c3);
        service.createInvoice(c4);
        //time
        ZonedDateTime start=DateTimeFormatConfiguration.String2Time("27,March,1119 ");
        ZonedDateTime end=DateTimeFormatConfiguration.String2Time("31,March,1119");
        //
        List<Invoice> list=service.getInvoicesByACustomerInAPeriod(cus1.getId(),start,end);
        if(list!=null) {
            for(int i=0;i<list.size();i++)
            {
                assertEquals(cus1.getId(),list.get(i).getBooking().getCustomer().getId() );//id of cus 1 should be in the list
                assertNotEquals(cus2.getId(),list.get(i).getBooking().getCustomer().getId() );//id of cus 2 should be in the list
                assertNotEquals(c4,list.get(i));//invoice 4 should not be in the list
            }
        }
    }

    @Test
    public void getInvoicesByADriverInAPeriod() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Testing subjects should be Isolated by date from the current Objects in database");
        System.out.println("Testing subjects has created time from 24,March,1119 to 30, March,1119");
        System.out.println("--------------------------------------------------------------------------------");
        Driver cus1=new Driver();
        Driver cus2=new Driver();
        Booking b1=new Booking().setDriver(cus1);
        Booking b2=new Booking().setDriver(cus2);
        Invoice c1=new Invoice().setBooking(b1).setCreatedDate(DateTimeFormatConfiguration.String2Time("28,March,1119") );
        Invoice c2=new Invoice().setBooking(b1).setCreatedDate(DateTimeFormatConfiguration.String2Time("26,March,1119") );
        Invoice c3=new Invoice().setBooking(b2).setCreatedDate(DateTimeFormatConfiguration.String2Time("25,March,1119") );
        Invoice c4=new Invoice().setBooking(b1).setCreatedDate(DateTimeFormatConfiguration.String2Time("24,May,1119") );
        //add
        service.createInvoice(c1);
        service.createInvoice(c2);
        service.createInvoice(c3);
        service.createInvoice(c4);
        //time
        ZonedDateTime start=DateTimeFormatConfiguration.String2Time("27,March,1119 ");
        ZonedDateTime end=DateTimeFormatConfiguration.String2Time("31,March,1119");
        //
        List<Invoice> list=service.getInvoicesByADriverInAPeriod(cus1.getId(),start,end);
        if(list!=null) {
            for(int i=0;i<list.size();i++)
            {
                assertEquals(cus1.getId(),list.get(i).getBooking().getDriver().getId() );//id of cus 1 should be in the list
                assertNotEquals(cus2.getId(),list.get(i).getBooking().getDriver().getId() );//id of cus 2 should be in the list
                assertNotEquals(c4,list.get(i));//invoice 4 should not be in the list
            }
        }

    }

    @Test
    public void createInvoice() {
        List<Invoice> before=sessionFactory.getCurrentSession().createQuery("from Invoice").list();
        Invoice i=new Invoice();
        service.createInvoice(i);//add new invoice
        List<Invoice> after=sessionFactory.getCurrentSession().createQuery("from Invoice").list();
        //assert
        assertNotEquals(after.size(),before.size());//size should be different
        assertTrue(after.size()-1== before.size());
        assertEquals(after.get(after.size()-1),i);
    }

    @Test
    public void updateInvoice() {
        List<Invoice> before=sessionFactory.getCurrentSession().createQuery("from Invoice").list();
        if(before==null||before.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        float oldRevenue=before.get(0).getRevenue();
        Invoice i=new Invoice().setRevenue(113);
        service.updateInvoice((long)1,i);
        List<Invoice> after=sessionFactory.getCurrentSession().createQuery("from Invoice").list();
        //assert
        assertEquals(before,after);//nothinbg should have changed
        //check the updated
        Invoice temp=service.getInvoiceById((long)1);
        assertTrue(113==temp.getRevenue());//check revenue
        assertFalse(oldRevenue== temp.getRevenue());//value has changed

    }

    @Test
    public void deleteInvoiceById() {
        List<Invoice> before=sessionFactory.getCurrentSession().createQuery("from Invoice").list();
        if(before==null||before.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        service.deleteInvoiceById((long)1);
        List<Invoice> after=sessionFactory.getCurrentSession().createQuery("from Invoice").list();
        assertFalse(after.size()==before.size());
        assertEquals(1,after.size()-before.size());
        assertNull(service.getInvoiceById((long)1));//should not be able to find this
    }

    @Test
    public void getInvoiceBetween() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Testing subjects should be Isolated by date from the current Objects in database");
        System.out.println("Testing subjects has created time from 24,March,1119 to 30, March,1119");
        System.out.println("--------------------------------------------------------------------------------");
        Invoice c1=new Invoice().setCreatedDate(DateTimeFormatConfiguration.String2Time("28,March,1119") );
        Invoice c2=new Invoice().setCreatedDate(DateTimeFormatConfiguration.String2Time("29,March,1119") );
        Invoice c3=new Invoice().setCreatedDate(DateTimeFormatConfiguration.String2Time("30,March,1119") );
        Invoice c4=new Invoice().setCreatedDate(DateTimeFormatConfiguration.String2Time("24,May,1119") );
        //add
        service.createInvoice(c1);
        service.createInvoice(c2);
        service.createInvoice(c3);
        service.createInvoice(c4);
        //time
        ZonedDateTime start=DateTimeFormatConfiguration.String2Time("27,March,1119 ");
        ZonedDateTime end=DateTimeFormatConfiguration.String2Time("31,March,1119");
        //
        List<Invoice> list=service.getInvoiceBetween(start,end);
        if(list!=null) {
            assertEquals(c1,list.get(0));
            assertEquals(c2,list.get(1));
            assertEquals(c3,list.get(2));
            for(int i=0;i<list.size();i++)
            {
                assertNotEquals(c4,list.get(i));//invoice 4 should not be in the list
            }
        }
    }

    @Test
    public void getTotalRevenue() {
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Testing subjects should be Isolated by date from the current Objects in database");
        System.out.println("Testing subjects has created time from 24,March,1119 to 30, March,1119");
        System.out.println("--------------------------------------------------------------------------------");
        Invoice c1=new Invoice().setRevenue(112).setCreatedDate(DateTimeFormatConfiguration.String2Time("28,March,1119") );
        Invoice c2=new Invoice().setRevenue(142).setCreatedDate(DateTimeFormatConfiguration.String2Time("29,March,1119") );
        Invoice c3=new Invoice().setRevenue(92).setCreatedDate(DateTimeFormatConfiguration.String2Time("30,March,1119") );
        Invoice c4=new Invoice().setRevenue(67).setCreatedDate(DateTimeFormatConfiguration.String2Time("24,May,1119") );
        //add
        service.createInvoice(c1);
        service.createInvoice(c2);
        service.createInvoice(c3);
        service.createInvoice(c4);
        ZonedDateTime start=DateTimeFormatConfiguration.String2Time("27,March,1119 ");
        ZonedDateTime end=DateTimeFormatConfiguration.String2Time("31,March,1119");
        float total=service.getTotalRevenue(start,end);
        System.out.println("total is:"+total);
        assertTrue((112+142+92)==total);
        assertFalse((112+142+92+67)==total);
        end=DateTimeFormatConfiguration.String2Time("29,March,1119");
        total=service.getTotalRevenue(start,end);
        assertTrue(112+142==total);
        assertFalse(112+142+92==total);
    }


}