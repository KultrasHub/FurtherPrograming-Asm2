package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class BookingController {
    @Autowired
    BookingService bookingService;

    @RequestMapping(path = "/bookings", method = RequestMethod.GET)
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @RequestMapping(path = "/bookings", method = RequestMethod.POST)
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) throws URISyntaxException {
        Booking newBooking =  bookingService.addBooking(booking);
        return ResponseEntity.created(new URI("/bookings/" + newBooking.getId())).body(booking);
    }


    @RequestMapping(path = "/bookings/{bookId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateBooking(@RequestBody Booking booking, @PathVariable("bookId") long bookId){
        bookingService.updateBookingById(bookId,booking);
        return ResponseEntity.ok().build();
    }

    //delete cars
    @RequestMapping(path = "/bookings/{bookId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBookingById(@PathVariable("bookId") long bookId){
        bookingService.deleteBookingById(bookId);
        return ResponseEntity.ok().build();
    }
}
