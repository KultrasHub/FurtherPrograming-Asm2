package Backend.Project.TaxiCompany.Service;

import Backend.Project.TaxiCompany.Config.DateTimeFormatConfiguration;
import Backend.Project.TaxiCompany.Exception.RecordNotFoundException;
import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Support.CarUsage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class BookingService {
    @Autowired
    SessionFactory sessionFactory;
    public  void setSessionFactory(SessionFactory sessionFactory){this.sessionFactory=sessionFactory;}
    //CRUD
    public List<Booking> getAllBookings() {
        List<Booking> list = sessionFactory.getCurrentSession().createQuery("from Booking").list();
        if(list!=null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<Booking>();
        }
    }
    public List<Booking> getBookingsByDate(String date)
    {
        ZonedDateTime start=DateTimeFormatConfiguration.getStartOfDate(date);
        ZonedDateTime end=DateTimeFormatConfiguration.getEndOfDate(date);
        //get result
        List<Booking> result = sessionFactory.getCurrentSession()
                .createQuery("from Booking B where B.createdDate between :start and :end")
                .setParameter("start", start)
                .setParameter("end",end)
                .list();
        if(result!=null && result.size() > 0) {
            return result;
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
            System.out.println("No Booking found for given ID");
            return null;
        }
    }

    public Booking createBooking(Booking bookingEntity) {
        sessionFactory.getCurrentSession().save(bookingEntity);
        return bookingEntity;
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
            booking.setCustomer(bookingEntity.getCustomer())
                    .setCar(bookingEntity.getCar())
                    .setDriver(bookingEntity.getDriver())
                    .setCreatedDate(bookingEntity.getCreatedDate());
            session.update(booking);
            return booking;
        } else {
            System.out.println("No Booking found for given ID");
            return null;
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
    public List<CarUsage> getCarUsedInAMonth(String monthInput)
    {
        //Get time zone from Date Time formatter
        ZonedDateTime[] period= DateTimeFormatConfiguration.getTimeStone(monthInput);
        if(period==null)
        {
            return  null;
        }
        //get List of booking between the time found
        List<Booking> result = sessionFactory.getCurrentSession()
                .createQuery("from Booking B where B.createdDate between :start and :end")
                .setParameter("start", period[0])
                .setParameter("end",period[1])
                .list();
        if(result==null||result.isEmpty())
        {
            return null;
        }
        //make list for counting cars
        ArrayList<CarUsage> carList=new ArrayList<CarUsage>();
        //loop through all booking
        for(int i=0;i<result.size();i++)
        {
            //loop through car list to add new car
            if(result.get(i).getCar()!=null)//check car
            {
                boolean carCounted=false;//reset
                for(int j=0;j<carList.size();j++)//loop through the car list to add usage to the correct card
                {
                    carCounted=carList.get(j).addUsageTime(result.get(i).getCar());
                    if(carCounted)
                    {
                        //move on to the next booking
                        break;
                    }
                }
                //if the loop is break will not call this
                if(!carCounted)
                {
                    //the car may not be found in the current list so add a new car usage to the list
                    CarUsage usage=new CarUsage();
                    usage.addUsageTime(result.get(i).getCar());
                    //add to list
                    carList.add(usage);
                }
            }
        }
        return carList;


    }
}
