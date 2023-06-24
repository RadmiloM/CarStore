package com.cars.RentACar.mapper;

import com.cars.RentACar.dto.ContractDTO;
import com.cars.RentACar.entity.Car;
import com.cars.RentACar.entity.Contract;
import com.cars.RentACar.entity.User;
import com.cars.RentACar.exception.CarNotFoundException;
import com.cars.RentACar.exception.UserNotFoundException;
import com.cars.RentACar.repository.CarRepository;
import com.cars.RentACar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContractMapper {
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public Contract mapToEntity(ContractDTO contractDTO) {
        Contract contract = new Contract();
        Optional<User> optionalUser = userRepository.findById(contractDTO.getUserId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id: " + contractDTO.getUserId() + " does not exists in database");
        }
        Optional<Car> optionalCar = carRepository.findById(contractDTO.getCarId());
        if (optionalCar.isEmpty()) {
            throw new CarNotFoundException("Car with id: " + contractDTO.getCarId() + " does not exists in database");
        }
        Car car = optionalCar.get();
        User user = optionalUser.get();
        contract.setUser(user);
        contract.setCar(car);
        contract.setStartDate(contractDTO.getStartDate());
        contract.setEndDate(contractDTO.getEndDate());
        contract.setTotalPrice(contractDTO.getTotalPrice());
        contract.setSigned(contractDTO.isSigned());
        return contract;

    }

    public ContractDTO mapToDTO(Contract contract){
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setUserId(contract.getUser().getUserId());
        contractDTO.setCarId(contract.getCar().getCarId());
        contractDTO.setStartDate(contract.getStartDate());
        contractDTO.setEndDate(contract.getEndDate());
        contractDTO.setTotalPrice(contract.getTotalPrice());
        contractDTO.setSigned(contract.isSigned());
        contractDTO.setApproved(contract.isApproved());
        return contractDTO;
    }

    public List<ContractDTO> mapToDTO(List<Contract> contractList){
        return contractList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


}
