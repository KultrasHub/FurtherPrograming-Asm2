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

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public List<Invoice> getAllInvoices() {
        List<Invoice> invoiceList = sessionFactory.getCurrentSession()
                .createQuery("from Invoice")
                .list();
        if(invoiceList.size() > 0) {
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
            System.out.println("called");
            throw new RecordNotFoundException("No invoice found for given ID");
        }
    }

    public List<Invoice> getInvoicesByACustomerInAPeriod(Long id, ZonedDateTime start, ZonedDateTime end, Integer page, Integer size){
        List result = sessionFactory.getCurrentSession()
                .createQuery("select B.invoice from Booking B where B.customer.id = :id")
                .setParameter("id", id)
                .setFirstResult(page)
                .setMaxResults(9)
                .list();
        return getNewInvoices(start, end, result);
    }

    private List<Invoice> getNewInvoices(ZonedDateTime start, ZonedDateTime end, List result) {
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

    public List<Invoice> getInvoicesByADriverInAPeriod(Long id, ZonedDateTime start, ZonedDateTime end, Integer page, Integer size){
        List result = sessionFactory.getCurrentSession()
                .createQuery("select B.invoice from Booking B where B.driver.id = :id")
                .setParameter("id", id)
                .setFirstResult(page)
                .setMaxResults(size)
                .list();
        return getNewInvoices(start, end, result);
    }

    public Invoice createInvoice(Invoice invoiceEntity) {
        sessionFactory.getCurrentSession().save(invoiceEntity);
        return invoiceEntity;
    }

    public Invoice updateInvoice(Long id, Invoice invoiceEntity) throws Exception{
        Session session = sessionFactory.getCurrentSession();
        List result =  session
                .createQuery("from Invoice I where I.id = :id")
                .setParameter("id", id)
                .list();
        if(result != null && !result.isEmpty()) {
            Invoice invoice = (Invoice) result.get(0);
            session.evict(invoice);
            invoice.setRevenue(invoiceEntity.getRevenue());
            session.update(invoice);
            return invoice;
        } else {
            throw new RecordNotFoundException("No invoice found for given ID!");
        }
    }

    public void deleteInvoiceById(Long id) {
        int result = sessionFactory.getCurrentSession()
                .createQuery("delete from Invoice I where I.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
    //Additional API
    public List<Invoice> listInvoiceBetween(ZonedDateTime start, ZonedDateTime end)
    {
        List<Invoice> result = sessionFactory.getCurrentSession()
                .createQuery("from Invoice")
                .list();
        if(result!=null&&!result.isEmpty())
        {
            return getNewInvoices(start, end, result);
        }
        return new ArrayList<Invoice>();
    }
    public  void saveInvoice(Invoice invoice)
    {
        sessionFactory.getCurrentSession().save(invoice);
    }
}
