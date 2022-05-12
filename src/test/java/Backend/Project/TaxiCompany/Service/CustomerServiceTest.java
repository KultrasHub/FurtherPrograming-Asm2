package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Model.Customer;
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
public class CustomerServiceTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    CustomerService service;
    @Test
    public void getAllCustomers() {
        List<Customer> list=service.getAllCustomers();
        List<Customer> test=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        if(list==null||list.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        //assert
        assertEquals(list,test);
        //remove test
        test.remove(0);
        assertNotEquals(list,test);
    }

    @Test
    public void getCustomerById() {
        Customer cus1=service.getCustomerById((long)1);
        Customer cus2=service.getCustomerById((long)2);
        List<Customer> list=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        if(list==null||list.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        if(cus1!=null)
        {
            assertEquals(list.get(0),cus1);
        }
        if(cus2!=null)
        {
            assertEquals(list.get(1),cus2);
            assertNotEquals(list.get(0),cus2);
            assertTrue(cus2.getId()==2);
        }
    }

    @Test
    public void getCustomerByName() {
        Customer cus=new Customer().setName("baka");
        service.createCustomer(cus);
        List<Customer> list=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        if(list==null||list.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        Customer cus1=service.getCustomerByName("baka");
        if(cus1!=null)
        {
            assertEquals("baka",list.get(list.size()-1).getName());//the just added should has the same name as cus1
            assertEquals(cus1.getId(),cus.getId());
        }

    }

    @Test
    public void getCustomerByAddress() {
        Customer cus=new Customer().setAddress("address");
        service.createCustomer(cus);
        List<Customer> list=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        if(list==null||list.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        Customer cus1=service.getCustomerByAddress("address");
        if(cus1!=null)
        {
            assertEquals("address",list.get(list.size()-1).getAddress());//the just added should has the same name as cus1
            assertEquals(cus1.getId(),cus.getId());
        }
    }

    @Test
    public void getCustomerByPhone() {
        Customer cus=new Customer().setPhone("12141234");
        service.createCustomer(cus);
        List<Customer> list=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        if(list==null||list.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        Customer cus1=service.getCustomerByPhone("12141234");
        if(cus1!=null)
        {
            assertEquals("12141234",list.get(list.size()-1).getPhone());//the just added should has the same name as cus1
            assertEquals(cus1.getId(),cus.getId());
        }
    }

    @Test
    public void createCustomer() {
        List<Customer> before=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        Customer customer=new Customer().setName("Tyrion");
        service.createCustomer(customer);
        List<Customer> after=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        //assert
        assertTrue(after.size()-1==before.size());//size should be increased by 1
        assertEquals(customer,after.get(before.size()));//the last added should be the customer
    }

    @Test
    public void updateCustomerById() {
        List<Customer> before=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        if(before==null||before.isEmpty())
        {
            System.out.println("-----------------------------------------------------");
            System.out.println("To Test this method there must be element in database");
            System.out.println("-----------------------------------------------------");
            return;
        }
        Customer oldCus=before.get(0);
        String oldName=oldCus.getName();
        long id=oldCus.getId();
        Customer newCus=new Customer().setName("Jory").setAddress("123_tada");
        service.updateCustomerById(id,newCus);
        List<Customer> after=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        //assert
        assertEquals(newCus.getName(),after.get(0).getName());//new Customer equal to after first
        assertEquals(before.size(),after.size());//no thing should be change in size
        assertNotEquals(after.get(0).getName(),oldName);//the cus has been change
    }

    @Test
    public void deleteCustomerById() {
        List<Customer> original=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        if(original==null||original.isEmpty())
        {
            System.out.println("You MUST add customer to database to test delete");
            return;
        }
        service.deleteCustomerById((long)1);
        List<Customer> after=sessionFactory.getCurrentSession().createQuery("from Customer").list();
        assertTrue(after.size()+1==original.size());//after is less 1
        assertNotEquals(after.get(0),original.get(0));//the first element is after is deleted
        assertNull(service.getCustomerById((long)1));//id 1 has been deleted
    }

}