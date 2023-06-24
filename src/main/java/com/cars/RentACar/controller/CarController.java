package com.cars.RentACar.controller;

import com.cars.RentACar.dto.CarDTO;
import com.cars.RentACar.entity.Car;
import com.cars.RentACar.entity.Contract;
import com.cars.RentACar.mapper.CarMapper;
import com.cars.RentACar.service.CarService;
import com.cars.RentACar.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    private final ContractService contractService;


    @GetMapping("/cars")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        List<CarDTO> carDTO = carMapper.mapToDTO(cars);
        return ResponseEntity.ok(carDTO);
    }


    @GetMapping("/cars/available/search")
    public ResponseEntity<List<CarDTO>> searchCars(@RequestParam(required = false) Integer year,
                                                   @RequestParam(required = false) String make,
                                                   @RequestParam(required = false) String model,
                                                   @RequestParam(required = false) Boolean automatic,
                                                   @RequestParam(required = false) Double price,
                                                   @RequestParam(required = false) Integer power,
                                                   @RequestParam(required = false) Integer doors) {
        List<Car> cars = carService.searchCar(year, make, model, automatic, price,power,doors);
        List<CarDTO> carDTO = carMapper.mapToDTO(cars);
        return ResponseEntity.ok(carDTO);
    }

    @GetMapping("/cars/{carId}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable UUID carId) {
        Car car = carService.getCarById(carId);
        CarDTO carDTO = carMapper.mapToDTO(car);
        return ResponseEntity.ok(carDTO);
    }

    @PatchMapping("/cars/{carId}")
    public ResponseEntity<Void> patchCar(@RequestBody CarDTO carDTO,
                                         @PathVariable UUID carId,
                                         @RequestHeader("id") UUID userId) {
        Car car = carMapper.mapToEntity(carDTO);
        carService.patchCar(car, carId,userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID carId,@RequestHeader("id") UUID userId) {
        carService.deleteCar(carId,userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/cars")
    public ResponseEntity<Void> createCar(@RequestBody CarDTO carDTO,@RequestHeader("id") UUID userId) {
        Car car = carMapper.mapToEntity(carDTO);
        carService.createCar(car,userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/cars/available")
    public ResponseEntity<List<CarDTO>> searchAvailableCars(@RequestParam
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                            @RequestParam
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                            @RequestParam(required = false) Integer year,
                                                            @RequestParam(required = false) String make,
                                                            @RequestParam(required = false) String model,
                                                            @RequestParam(required = false) Boolean automatic,
                                                            @RequestParam(required = false) Double price,
                                                            @RequestParam(required = false) Integer power,
                                                            @RequestParam(required = false) Integer doors) {
        List<Car> cars = contractService.searchAvailableCars(startDate, endDate, year, make, model, automatic, price, power, doors);
        List<CarDTO> carD = carMapper.mapToDTO(cars);
        return ResponseEntity.ok(carD);
    }


    @GetMapping("/cars/{carId}/calendar")
    public ResponseEntity<List<LocalDate>> getUnavailableCarDates(@PathVariable UUID carId) {
        List<Contract> contractDates = contractService.searchUnavailableCarDates(carId);
        List<LocalDate> dates = new ArrayList<>();
        for (var date : contractDates) {
            dates.add(date.getStartDate());
            dates.add(date.getEndDate());
        }
        return ResponseEntity.ok(dates);
    }


}
