package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Exception.RecordNotFoundException;
import Backend.Project.TaxiCompany.Model.Booking;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class BookingService {
    @Autowired
    SessionFactory sessionFactory;
    public  void setSessionFactory(SessionFactory sessionFactory){this.sessionFactory=sessionFactory;}
    public Booking addBooking(Booking booking)
    {
        sessionFactory.getCurrentSession().save(booking);
        return booking;
    }
    //CRUD
    public List<Booking> getAllBookings() {
        List<Booking> list = sessionFactory.getCurrentSession().createQuery("from Booking").list();
        if(list.size() > 0) {
            return list;
        } else {
            return new ArrayList<Booking>();
        }
    }

    public Booking getBookingById(Long id) throws RecordNotFoundException {
        List result = sessionFactory.getCurrentSession()
                .createQuery("from Booking B where B.id = :id")
                .setParameter("id", id)
                .list();

        if(result != null && !result.isEmpty()) {
            return (Booking) result.get(0);
        } else {
            throw new RecordNotFoundException("No Booking found for given ID");
        }
    }

    public Booking createBooking(Booking driverEntity) {
        sessionFactory.getCurrentSession().save(driverEntity);
        return driverEntity;
    }

    public Booking updateBookingById(Long id, Booking bookingEntity) {
        Session session = sessionFactory.getCurrentSession();
        List result =  session
                .createQuery("from Booking I where I.id = :id")
                .setParameter("id", id)
                .list();
        if(result != null && !result.isEmpty()) {
            Booking booking = (Booking) result.get(0);
            session.evict(booking);
            //driver.setName(driverEntity.getName());
            booking.setCustomer(bookingEntity.getCustomer());
            booking.setCar(bookingEntity.getCar());
            booking.setDriver(bookingEntity.getDriver());
            session.update(booking);
            return booking;
        } else {
            throw new RecordNotFoundException("No Booking found for given ID");
        }
    }

    public void deleteBookingById(Long id) {
        int result = sessionFactory.getCurrentSession()
                .createQuery("delete from Booking B where B.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    //Additional API
    public List<Booking> getBookingInAPeriod(ZonedDateTime start,ZonedDateTime end)
    {
        List<Booking> result = sessionFactory.getCurrentSession()
                .createQuery("from Booking B where B.createdDate between :start and :end")
                .setParameter("start", start)
                .setParameter("end",end)
                .list();
        if(result!=null&&!result.isEmpty())
        {
            return result;
        }
        return new ArrayList<Booking>();
    }

    public void saveBooking(Booking booking) {
        sessionFactory.getCurrentSession().save(booking);
    }
}
