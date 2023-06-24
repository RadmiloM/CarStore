package com.cars.RentACar.service;

import com.cars.RentACar.entity.Car;
import com.cars.RentACar.entity.User;
import com.cars.RentACar.exception.CarNotFoundException;
import com.cars.RentACar.exception.UserNotAllowed;
import com.cars.RentACar.exception.UserNotFoundException;
import com.cars.RentACar.repository.CarRepository;
import com.cars.RentACar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public void createCar(Car car,UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " was not found in database");
        }
        User userDB = optionalUser.get();
        if(null == userDB.getRole() || !userDB.getRole().equals("ADMIN")){
            throw new UserNotAllowed("User with id " + userId + " is not allowed to access this resource");
        }
        carRepository.save(car);
    }

    public Car getCarById(UUID id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            throw new CarNotFoundException("Car with id: " + id + " does not exists in database");
        }
        return optionalCar.get();
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> searchCar(Integer year, String make, String model, Boolean automatic, Double price, Integer power, Integer doors) {
        return carRepository.searchCar(year, make, model, automatic, price, power, doors);
    }

    public void patchCar(Car car, UUID id,UUID userId) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            throw new CarNotFoundException("Car with id: " + id + " does not exists in database");
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " was not found in database");
        }
        Car carDB = optionalCar.get();
        User userDB = optionalUser.get();
        if(null == userDB.getRole() || !userDB.getRole().equals("ADMIN")){
            throw new UserNotAllowed("User with id " + userId + " is not allowed to access this resource");
        }


        if (null != car.getLicencePlate() && !"".equals(car.getLicencePlate())) {
            carDB.setLicencePlate(car.getLicencePlate());
        }
        if (null != car.getMake() && !"".equals(car.getMake())) {
            carDB.setMake(car.getMake());
        }
        if (null != car.getModel() && !"".equals(car.getModel())) {
            carDB.setModel(car.getModel());
        }
        if (car.getYear() != 0) {
            carDB.setYear(car.getYear());
        }
        if (car.getEngineCapacity() != 0) {
            carDB.setEngineCapacity(car.getEngineCapacity());
        }

        if (null != car.getColor() && !"".equals(car.getColor())) {
            carDB.setColor(car.getColor());
        }

        if (car.getPrice() != 0) {
            carDB.setPrice(car.getPrice());
        }
        if (car.getDoors() != 0) {
            carDB.setDoors(car.getDoors());
        }
        if (car.getSize() != 0) {
            carDB.setSize(car.getSize());
        }
        if (car.getPower() != 0) {
            carDB.setPower(car.getPower());
        }
        carDB.setAutomatic(car.isAutomatic());
        if (null != car.getFuel() && !"".equals(car.getFuel())) {
            carDB.setFuel(car.getFuel());
        }
        if (null != car.getImage() && !"".equals(car.getImage())) {
            carDB.setImage(car.getImage());
        }
        carRepository.save(carDB);
    }

    public void deleteCar(UUID carId,UUID userId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isEmpty()) {
            throw new CarNotFoundException("Car with id: " + carId + " does not exists in database");
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " was not found in database");
        }
        User userDB = optionalUser.get();
        if(null == userDB.getRole() || !userDB.getRole().equals("ADMIN")){
            throw new UserNotAllowed("User with id " + userId + " is not allowed to access this resource");
        }
        carRepository.deleteById(carId);
    }


}
