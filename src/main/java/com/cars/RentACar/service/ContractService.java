package com.cars.RentACar.service;

import com.cars.RentACar.entity.Car;
import com.cars.RentACar.entity.Contract;
import com.cars.RentACar.entity.User;
import com.cars.RentACar.exception.CarNotFoundException;
import com.cars.RentACar.exception.ContractNotFoundException;
import com.cars.RentACar.exception.UserNotAllowed;
import com.cars.RentACar.exception.UserNotFoundException;
import com.cars.RentACar.repository.CarRepository;
import com.cars.RentACar.repository.ContractRepository;
import com.cars.RentACar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    public void createContract(Contract contract) {
        contractRepository.save(contract);
    }

    public List<Car> searchAvailableCars(LocalDate startDate,
                                         LocalDate endDate,
                                         Integer year,
                                         String make,
                                         String model,
                                         Boolean automatic,
                                         Double price,
                                         Integer power,
                                         Integer doors) {
        return contractRepository.findByStartDateBetween(startDate, endDate, year, make, model, automatic, price, power, doors);
    }

    public List<Contract> searchUnavailableCarDates(UUID uuid) {
        Optional<Car> optionalCar = carRepository.findById(uuid);
        if (optionalCar.isEmpty()) {
            throw new CarNotFoundException("Car with id: " + uuid + " does not exists in database");
        }
        return contractRepository.findByCarId(uuid);
    }

    public void getContractApproval(UUID uuid, UUID adminId, Boolean approval) {
        Optional<Contract> contractOptional = contractRepository.findById(uuid);
        if (contractOptional.isEmpty()) {
            throw new ContractNotFoundException("Contract with id: " + uuid + " does not exists in database");
        }

        Optional<User> optionalAdmin = userRepository.findById(adminId);
        if (optionalAdmin.isEmpty()) {
            throw new UserNotFoundException("Admin with id: " + adminId + " was not found in database");
        }

        User adminDB = optionalAdmin.get();
        if (null == adminDB.getRole() || !adminDB.getRole().equals("ADMIN")) {
            throw new UserNotAllowed("Provided id: " + adminId + " can not access this resource");
        }
        Contract contract = contractOptional.get();
        contract.setApproved(approval);
        if (contract.isApproved()) {
            contractRepository.save(contract);
        } else {
            contractRepository.deleteById(uuid);
        }
    }

    public List<Contract> getSamples(UUID userId, UUID carId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id: " + userId + " does not exists in database");
        }
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isEmpty()) {
            throw new CarNotFoundException("Car with id: " + carId + " does not exists in database");
        }
        return contractRepository.searchContractsWhichClientCanSign(userId, carId);
    }

    public List<Contract> getAllContracts(UUID adminId) {
        Optional<User> optionalAdmin = userRepository.findById(adminId);
        if (optionalAdmin.isEmpty()) {
            throw new UserNotFoundException("Admin with id " + adminId + " was not found in database");
        }
        User adminDB = optionalAdmin.get();
        if (null == adminDB.getRole() || !adminDB.getRole().equals("ADMIN")) {
            throw new UserNotAllowed("User with id " + adminId + " is not allowed to access this resource");
        }
        return contractRepository.findAll();
    }

    public List<Contract> fetchUnsignedContracts(UUID uuid) {
        Optional<User> optionalUser = userRepository.findById(uuid);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id " + uuid + " was not found in database");
        }

        User adminDB = optionalUser.get();
        if (null == adminDB.getRole() || !adminDB.getRole().equals("ADMIN")) {
            throw new UserNotAllowed("User with id " + uuid + " is not allowed to access this resource");
        }

        return contractRepository.searchUnsignedContracts();
    }

    public List<Contract> fetchClientContracts(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " was not found in database");
        }
        return contractRepository.fetchClientContracts(userId);
    }
}
