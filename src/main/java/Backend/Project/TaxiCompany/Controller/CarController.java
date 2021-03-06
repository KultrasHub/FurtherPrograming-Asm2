package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Exception.InvalidRequestException;
import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Model.Customer;
import Backend.Project.TaxiCompany.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CarController {
    @Autowired
    private CarService carService;

    //Get all car for admin
    @RequestMapping(path = "/cars", method = RequestMethod.GET)
    public List<Car> getAllCar(){
        return carService.getAllCars();
    }

    @RequestMapping(path = "/cars/{id}", method = RequestMethod.GET)
    public Car getCarById(@PathVariable("id") Long id) {
        Car car = carService.getCarById(id);
        return car;
    }
    //admin can create a new car
    @RequestMapping(path = "/cars", method = RequestMethod.POST)
    public ResponseEntity<Car> createdCar(@RequestBody Car car) throws Exception {
        if(car == null) {
            throw new InvalidRequestException("Must not be null!");
        }
        Car newCar =  carService.createCar(car);
        return ResponseEntity.created(new URI("/cars/" + newCar.getId())).body(car);
    }


    @RequestMapping(path = "/cars/{carId}", method = RequestMethod.PUT)
    public ResponseEntity<Car> updateCar(@RequestBody Car car, @PathVariable long carId) throws Exception{
        if(car == null) {
            throw new InvalidRequestException("Must not be null!");
        }
        Car c=carService.updateCarById(carId,car);
        return new  ResponseEntity<Car>(c, new HttpHeaders(), HttpStatus.OK);
    }

    //delete cars
    @RequestMapping(path = "/cars/{carId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCarById(@PathVariable long carId){
        carService.deleteCarById(carId);
        return ResponseEntity.ok().build();
    }


}
