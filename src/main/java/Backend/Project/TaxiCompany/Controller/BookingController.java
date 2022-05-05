package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class BookingController {
    @Autowired
    BookingService bookingService;
    //Get all car for admin
    @RequestMapping(path = "/admin/booking", method = RequestMethod.GET)
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    //admin can create a new car
    @RequestMapping(path = "/admin/booking", method = RequestMethod.POST)
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) throws URISyntaxException {
        Booking newBooking =  bookingService.addBooking(booking);
        return ResponseEntity.created(new URI("/admin/bookings/" + newBooking.getId())).body(booking);
    }


    @RequestMapping(path = "/admin/booking/{bookId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateBooking(@RequestBody Booking booking, @PathVariable long bookId){
        bookingService.updateBookingById(bookId,booking);
        return ResponseEntity.ok().build();
    }

    //delete cars
    @RequestMapping(path = "/admin/booking/{bookId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBookingById(@PathVariable long bookId){
        bookingService.deleteBookingById(bookId);
        return ResponseEntity.ok().build();
    }
}
