package com.cars.RentACar.controller;

import com.cars.RentACar.dto.ContractDTO;
import com.cars.RentACar.dto.ContractSignRequestDTO;
import com.cars.RentACar.dto.ContractSignResponseDTO;
import com.cars.RentACar.entity.Contract;
import com.cars.RentACar.mapper.ContractMapper;
import com.cars.RentACar.mapper.ContractSignMapper;
import com.cars.RentACar.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ContractMapper contractMapper;

    private final ContractSignMapper contractSignMapper;

    @PostMapping("/contracts/sample")
    public ResponseEntity<List<ContractSignResponseDTO>> signContract(@Valid @RequestBody ContractSignRequestDTO contractSignDTO) {
        List<Contract> contracts = contractService.getSamples(contractSignDTO.getUserId(), contractSignDTO.getCarId());
        List<ContractSignResponseDTO> contractSignResponseDTOS = contractSignMapper.mapToDTO(contracts);
        return ResponseEntity.ok(contractSignResponseDTOS);
    }


    @PostMapping("/contracts")
    public ResponseEntity<Void> createContract(@Valid @RequestBody ContractDTO contractDTO) {
        Contract contract = contractMapper.mapToEntity(contractDTO);
        contractService.createContract(contract);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/contracts")
    public ResponseEntity<List<ContractDTO>> getAllContracts(@RequestHeader("id") UUID adminId) {
        List<Contract> contracts = contractService.getAllContracts(adminId);
        List<ContractDTO> contractDTOS = contractMapper.mapToDTO(contracts);
        return ResponseEntity.ok(contractDTOS);
    }

    @GetMapping("/contracts/pending")
    public ResponseEntity<List<ContractDTO>> searchUnsignedContracts(@RequestHeader("id") UUID adminId) {
        List<Contract> contracts = contractService.fetchUnsignedContracts(adminId);
        List<ContractDTO> contractDTOS = contractMapper.mapToDTO(contracts);
        return ResponseEntity.ok(contractDTOS);
    }


    @PostMapping("/contracts/{contractId}/approval")
    public ResponseEntity<Void> approveContract(@PathVariable UUID contractId,
                                                @RequestHeader("id") UUID adminId,
                                                @RequestParam("approval") Boolean approval) {
        contractService.getContractApproval(contractId,adminId,approval);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contracts/{userId}/history")
    public ResponseEntity<List<ContractDTO>> getClientHistory(@PathVariable UUID userId) {
        List<Contract> contracts = contractService.fetchClientContracts(userId);
        List<ContractDTO> contractDTOS = contractMapper.mapToDTO(contracts);
        return ResponseEntity.ok(contractDTOS);
    }


}
