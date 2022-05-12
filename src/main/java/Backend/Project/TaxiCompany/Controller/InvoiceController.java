package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Exception.InvalidRequestException;
import Backend.Project.TaxiCompany.Exception.RecordNotFoundException;
import Backend.Project.TaxiCompany.Model.Invoice;
import Backend.Project.TaxiCompany.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping(("/invoices"))
public class InvoiceController {
    @Autowired
    InvoiceService service;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices(
            @RequestParam( name = "start", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
            @RequestParam( name = "end", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end ) {
        if(start != null && end!= null){
            List<Invoice> list = service.getAllInvoices();
            return new ResponseEntity<List<Invoice>>(list, new HttpHeaders(), HttpStatus.OK);
        }
        List<Invoice> list = service.getAllInvoices();

        return new ResponseEntity<List<Invoice>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Invoice invoice = service.getInvoiceById(id);

        return new ResponseEntity<Invoice>(invoice, new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/period")
    public ResponseEntity<List<Invoice>> getInvoiceInAPeriod(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end)
    {
        List<Invoice> invoices=service.getInvoiceBetween(start,end);
        return new ResponseEntity<List<Invoice>>(invoices,new HttpHeaders(),HttpStatus.OK);
    }
    @GetMapping("/c")
    public ResponseEntity<List<Invoice>> getInvoicesByACustomerInAPeriod(
            @RequestParam("id") Long id,
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "9") Integer size) {
        List<Invoice> invoices = service.getInvoicesByACustomerInAPeriod(id, start, end, page, size);

        return new ResponseEntity<List<Invoice>>(invoices, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/d")
    public ResponseEntity<List<Invoice>> getInvoicesByADriverInAPeriod(
            @RequestParam("id") Long id,
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "9") Integer size) {
        List<Invoice> invoices = service.getInvoicesByADriverInAPeriod(id, start, end, page, size);

        return new ResponseEntity<List<Invoice>>(invoices, new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/revenue")
    public float getTotalRevenue(
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime start,
            @RequestParam("end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime end) {
        float revenue = service.getTotalRevenue(start,end);

        return revenue;
    }
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice created = service.createInvoice(invoice);
        ResponseEntity<Invoice> response = new ResponseEntity<Invoice>(created, new HttpHeaders(), HttpStatus.OK);
        return response;
    }

    @PutMapping
    public ResponseEntity<Invoice> updateInvoiceById(@RequestBody Invoice invoice) throws Exception {
        if(invoice == null || invoice.getId() == null) {
            throw new InvalidRequestException("Must not be null!");
        }
        Invoice updated = service.updateInvoice(invoice.getId(), invoice);

        return new ResponseEntity<Invoice>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteInvoiceById(@PathVariable("id") Long id) throws RecordNotFoundException {
        service.deleteInvoiceById(id);
        return HttpStatus.OK;
    }
}
