package com.example.taxicompany.service;

import com.example.taxicompany.exception.RecordNotFoundException;
import com.example.taxicompany.model.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InvoiceService {
    @Autowired
    private SessionFactory sessionFactory;

   public List<Invoice> getAllInvoices() {
       List<Invoice> invoiceList = sessionFactory.getCurrentSession().createQuery("from Invoice").list();
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
           throw new RecordNotFoundException("No invoice found for given ID");
       }
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
           invoice.setRevenue(invoiceEntity.getRevenue());
           invoice.setBooking(invoiceEntity.getBooking());
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
}
