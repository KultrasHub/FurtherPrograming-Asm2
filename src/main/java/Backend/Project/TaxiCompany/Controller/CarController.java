package Backend.Project.TaxiCompany.Controller;

import Backend.Project.TaxiCompany.Model.Car;
import Backend.Project.TaxiCompany.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(path = "/admin/cars", method = RequestMethod.GET)
    public List<Car> getAllCar(){
        return carService.getAllCar();
    }

    //admin can create a new car
    @RequestMapping(path = "/admin/cars", method = RequestMethod.POST)
    public ResponseEntity<Car> createdCar(@RequestBody Car car) throws URISyntaxException {
        Car newCar =  carService.addCar(car);
        return ResponseEntity.created(new URI("/admin/cars/" + newCar.getId())).body(car);
    }


    @RequestMapping(path = "/admin/cars/{carId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> editCar(@RequestBody Car car, @PathVariable long carId){
        carService.updateCar(carId,car);
        return ResponseEntity.ok().build();
    }

    //delete cars
    @RequestMapping(path = "/admin/cars/{carId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCarById(@PathVariable long carId){
        carService.deleteCar(carId);
        return ResponseEntity.ok().build();
    }


}
