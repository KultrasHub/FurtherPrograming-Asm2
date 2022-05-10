package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Model.Booking;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class BookingController {
    @Autowired
    BookingService bookingService;

    @RequestMapping(path = "/bookings", method = RequestMethod.GET)
    public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> list= bookingService.getAllBookings();
        return new ResponseEntity<List<Booking>>(list, new HttpHeaders(), HttpStatus.OK);
    }
    @RequestMapping(path = "/bookings/{id}", method = RequestMethod.GET)
    public Booking getCustomerById(@PathVariable("id") Long id) {
        Booking booking = bookingService.getBookingById(id);
        return booking;
    }
    @RequestMapping(path = "/bookings", method = RequestMethod.POST)
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) throws URISyntaxException {
        Booking newBooking =  bookingService.createBooking(booking);
        return ResponseEntity.created(new URI("/bookings/" + newBooking.getId())).body(booking);
    }
    @RequestMapping(path = "/bookings/a", method = RequestMethod.GET)
    public ResponseEntity<List<Booking>> getBookingInAPeriod(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {
        List<Booking> bookings = bookingService.getBookingInAPeriod( start, end);

        return new ResponseEntity<List<Booking>>(bookings, new HttpHeaders(), HttpStatus.OK);
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
