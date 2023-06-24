package com.cars.RentACar.mapper;


import com.cars.RentACar.dto.CarDTO;
import com.cars.RentACar.entity.Car;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {

    public CarDTO mapToDTO(Car car){
        CarDTO carDTO = new CarDTO();
        carDTO.setLicencePlate(car.getLicencePlate());
        carDTO.setMake(car.getMake());
        carDTO.setModel(car.getModel());
        carDTO.setYear(car.getYear());
        carDTO.setEngineCapacity(car.getEngineCapacity());
        carDTO.setColor(car.getColor());
        carDTO.setPrice(car.getPrice());
        carDTO.setDoors(car.getDoors());
        carDTO.setSize(car.getSize());
        carDTO.setPower(car.getPower());
        carDTO.setAutomatic(car.isAutomatic());
        carDTO.setFuel(car.getFuel());
        carDTO.setImage(car.getImage());
        return carDTO;
    }

    public Car mapToEntity(CarDTO carDTO){
        Car car = new Car();
        car.setLicencePlate(carDTO.getLicencePlate());
        car.setMake(carDTO.getMake());
        car.setModel(carDTO.getModel());
        car.setYear(carDTO.getYear());
        car.setEngineCapacity(carDTO.getEngineCapacity());
        car.setColor(carDTO.getColor());
        car.setPrice(carDTO.getPrice());
        car.setDoors(carDTO.getDoors());
        car.setSize(carDTO.getSize());
        car.setPower(carDTO.getPower());
        car.setAutomatic(carDTO.isAutomatic());
        car.setFuel(carDTO.getFuel());
        car.setImage(carDTO.getImage());
        return car;
    }

    public List<CarDTO> mapToDTO(List<Car> cars){
        return cars.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
