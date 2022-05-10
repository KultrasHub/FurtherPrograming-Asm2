package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Exception.RecordNotFoundException;
import Backend.Project.TaxiCompany.Model.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InvoiceService {
    @Autowired
    private SessionFactory sessionFactory;
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
   public List<Invoice> getAllInvoices() {
       List<Invoice> invoiceList = sessionFactory.getCurrentSession().createQuery("from Invoice").list();
       if(invoiceList!=null && invoiceList.size() > 0) {
           return invoiceList;
       } else {
           return new ArrayList<Invoice>();
       }
   }

   public Invoice getInvoiceById(Long id) throws RecordNotFoundException {
       List result = sessionFactory.getCurrentSession()
               .createQuery("from Invoice I where I.id = :id")
               .setParameter("id", id)
               .list();

       if(result != null && !result.isEmpty()) {
           return (Invoice) result.get(0);
       } else {
           throw new RecordNotFoundException("No invoice found for given ID");
       }
   }

   public List<Invoice> getInvoicesByACustomerInAPeriod(Long id, ZonedDateTime start, ZonedDateTime end){
       System.out.println(id + " " + start + end);
        List result = sessionFactory.getCurrentSession()
                .createQuery("select I.booking from Invoice I where I.booking.customer.id = :id")
                .setParameter("id", id)
                .list();
        if(result != null && !result.isEmpty()) {
            ArrayList<Invoice> invoices = new ArrayList<>();
            for(int i = 0; i < result.size(); i++){
                Invoice invoice = (Invoice) result.get(i);
                ZonedDateTime date = invoice.getCreatedDate();
                if(date.isAfter(start) && date.isBefore(end)){
                    invoices.add(invoice);
                }
            }
            return invoices;
        }
        return new ArrayList<Invoice>();
   }

   public List<Invoice> getInvoicesByADriverInAPeriod(Long id, ZonedDateTime start, ZonedDateTime end){
       System.out.println(id + " " + start + end);
        List result = sessionFactory.getCurrentSession()
                .createQuery("select B.invoice from Booking B where B.driver.id = :id")
                .setParameter("id", id)
                .list();
        if(result != null && !result.isEmpty()) {
            ArrayList<Invoice> invoices = new ArrayList<>();
            for(int i = 0; i < result.size(); i++){
                Invoice invoice = (Invoice) result.get(i);
                ZonedDateTime date = invoice.getCreatedDate();
                if(date.isAfter(start) && date.isBefore(end)){
                    invoices.add(invoice);
                }
            }
            return invoices;
        }
        return new ArrayList<Invoice>();
   }

   public Invoice createInvoice(Invoice invoiceEntity) {
       sessionFactory.getCurrentSession().save(invoiceEntity);
       return invoiceEntity;
   }

   public Invoice updateInvoice(Long id, Invoice invoiceEntity) {
       Session session = sessionFactory.getCurrentSession();
       List result =  session
               .createQuery("from Invoice I where I.id = :id")
               .setParameter("id", id)
               .list();
       if(result != null && !result.isEmpty()) {
           Invoice invoice = (Invoice) result.get(0);
           session.evict(invoice);
           invoice.setRevenue(invoiceEntity.getRevenue())
                   .setCreatedDate(invoiceEntity.getCreatedDate());
           session.update(invoice);
           return invoice;
       } else {
           throw new RecordNotFoundException("No invoice found for given ID");
       }
   }

   public void deleteInvoiceById(Long id) {
       int result = sessionFactory.getCurrentSession()
               .createQuery("delete from Invoice I where I.id = :id")
               .setParameter("id", id)
               .executeUpdate();
   }
    //Additional API
    public List<Invoice> getInvoiceBetween(ZonedDateTime start, ZonedDateTime end)
    {
        List<Invoice> result = sessionFactory.getCurrentSession()
                .createQuery("from Invoice I where I.createdDate between :start and :end")
                .setParameter("start", start)
                .setParameter("end",end)
                .list();
        if(result!=null&&!result.isEmpty())
        {
            return result;
        }
        return new ArrayList<Invoice>();
    }
    //get total revenue
    public float getTotalRevenue(ZonedDateTime start, ZonedDateTime end)
    {
        float result=0;
        List<Invoice> invoicesFound=getInvoiceBetween(start,end);
        for(int i=0;i<invoicesFound.size();i++)
        {
            result+=invoicesFound.get(i).getRevenue();
        }
        return result;
    }

    //
   public  void saveInvoice(Invoice invoice)
   {
       sessionFactory.getCurrentSession().save(invoice);
   }
}
