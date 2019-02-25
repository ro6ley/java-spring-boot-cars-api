package com.example.cars.controllers;

import com.example.cars.exceptions.ResourceNotFoundException;
import com.example.cars.models.Car;
import com.example.cars.repository.CarRepository;

import io.swagger.annotations.ApiOperation; 

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Car controller.
 *
 * @author Robley Gori - ro6ley.github.io
 */
@RestController
@RequestMapping("/api/v1")
public class CarController {

  @Autowired
  private CarRepository carRepository;

  /**
   * Get all cars list.
   *
   * @return the list
   */
  @ApiOperation(value = "Fetches all cars in the database.", response = Car.class)
  @GetMapping("/cars")   // GET Method for reading operation
  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  /**
   * Gets cars by id.
   *
   * @param carId the car id
   * @return the cars by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @ApiOperation(value = "Fetches a single car by its id.", response = Car.class)
  @GetMapping("/cars/{id}")    // GET Method for Read operation
  public ResponseEntity<Car> getCarsById(@PathVariable(value = "id") Long carId)
      throws ResourceNotFoundException {

    Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car " + carId + " not found"));
    return ResponseEntity.ok().body(car);
  }

  /**
   * Create car car.
   *
   * @param car the car
   * @return the car
   */
  @ApiOperation(value = "Handles the creation of a car.", response = Car.class)
  @PostMapping("/cars")     // POST Method for Create operation
  public Car createCar(@Valid @RequestBody Car car) {
    return carRepository.save(car);
  }

  /**
   * Update car response entity.
   *
   * @param carId the car id
   * @param carDetails the car details
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @ApiOperation(value = "Handles the editing of a single car's details.", response = Car.class)
  @PutMapping("/cars/{id}")    // PUT Method for Update operation
  public ResponseEntity<Car> updateCar(
      @PathVariable(value = "id") Long carId, @Valid @RequestBody Car carDetails)
      throws ResourceNotFoundException {

    Car car = carRepository
                .findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car " + carId + " not found"));

    car.setCarName(carDetails.getCarName());
    car.setDoors(carDetails.getDoors());

    final Car updatedCar = carRepository.save(car);
    return ResponseEntity.ok(updatedCar);
  }

  /**
   * Delete car map.
   *
   * @param carId the car id
   * @return the map of the deleted car
   * @throws Exception the exception
   */
  @ApiOperation(value = "Handles the deletion of a single car by its id.", response = Car.class)
  @DeleteMapping("/car/{id}")    // DELETE Method for Delete operation
  public Map<String, Boolean> deleteCar(@PathVariable(value = "id") Long carId) throws Exception {
    Car car = carRepository
                .findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car " + carId + " not found"));

    carRepository.delete(car);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
