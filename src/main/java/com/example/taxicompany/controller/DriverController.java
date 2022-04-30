package com.example.taxicompany.controller;

import com.example.taxicompany.exception.RecordNotFoundException;
import com.example.taxicompany.model.Driver;
import com.example.taxicompany.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    DriverService service;

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> list = service.getAllDrivers();

        return new ResponseEntity<List<Driver>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("id") Long id) {
        Driver driver = service.getDriverById(id);
        return new ResponseEntity<Driver>(driver, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        Driver created = service.createDriver(driver);
        return new ResponseEntity<Driver>(created, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriverById(@PathVariable("id") Long id,@RequestBody Driver driver)  throws RecordNotFoundException {
        Driver updated = service.updateDriverById(id, driver);
        return new ResponseEntity<Driver>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteDriverById(@PathVariable("id") Long id, @RequestBody Driver driver) throws RecordNotFoundException {
        service.deleteDriverById(id);
        return HttpStatus.OK;
    }
}
