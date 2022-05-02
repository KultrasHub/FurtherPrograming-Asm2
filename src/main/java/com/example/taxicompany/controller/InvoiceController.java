package com.example.taxicompany.controller;

import com.example.taxicompany.exception.RecordNotFoundException;
import com.example.taxicompany.model.Invoice;
import com.example.taxicompany.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/invoices"))
public class InvoiceController {
    @Autowired
    InvoiceService service;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> list = service.getAllInvoices();

        return new ResponseEntity<List<Invoice>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Invoice invoice = service.getInvoiceById(id);

        return new ResponseEntity<Invoice>(invoice, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice created = service.createInvoice(invoice);
        return new ResponseEntity<Invoice>(created, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoiceById(@PathVariable("id") Long id, @RequestBody Invoice invoice) throws RecordNotFoundException {
        Invoice updated = service.updateInvoice(id, invoice);

        return new ResponseEntity<Invoice>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteInvoiceById(@PathVariable("id") Long id) throws RecordNotFoundException {
        service.deleteInvoiceById(id);
        return HttpStatus.OK;
    }
}
