package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Model.Booking;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BookingService {
    @Autowired
    SessionFactory sessionFactory;
    public  void setSessionFactory(SessionFactory sessionFactory){this.sessionFactory=sessionFactory;}
    public  void saveBooking(Booking booking)
    {
        sessionFactory.getCurrentSession().save(booking);
    }
}
