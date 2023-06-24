package com.cars.RentACar.mapper;

import com.cars.RentACar.dto.ContractSignResponseDTO;
import com.cars.RentACar.entity.Contract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractSignMapper {

    public ContractSignResponseDTO mapToDTO(Contract contract){
        ContractSignResponseDTO contractSignResponseDTO = new ContractSignResponseDTO();
        contractSignResponseDTO.setUserId(contract.getUser().getUserId());
        contractSignResponseDTO.setCarId(contract.getCar().getCarId());
        contractSignResponseDTO.setStartDate(contract.getStartDate());
        contractSignResponseDTO.setEndDate(contract.getEndDate());
        contractSignResponseDTO.setTotalPrice(contract.getTotalPrice());
        contractSignResponseDTO.setSigned(contract.isSigned());
        return contractSignResponseDTO;
    }

    public List<ContractSignResponseDTO> mapToDTO(List<Contract> contracts){
        return contracts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
