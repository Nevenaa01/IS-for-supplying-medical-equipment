package com.example.ISA2023.back.services;

import com.example.ISA2023.back.dtos.ContractDto;
import com.example.ISA2023.back.models.Company;
import com.example.ISA2023.back.models.Contract;
import com.example.ISA2023.back.models.Equipment;
import com.example.ISA2023.back.models.irepositories.CompanyRepository;
import com.example.ISA2023.back.models.irepositories.IContractRepository;
import com.example.ISA2023.back.models.irepositories.IEquipmentRepository;
import com.example.ISA2023.back.user.IUserRepository;
import com.example.ISA2023.back.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContractService {
    private final IContractRepository contractRepository;
    private final IUserRepository userRepository;
    private final IEquipmentRepository equipmentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public ContractService(IContractRepository contractRepository,IUserRepository userRepository,IEquipmentRepository equipmentRepository) {
        this.contractRepository = contractRepository;
        this.userRepository =   userRepository;
        this.equipmentRepository=equipmentRepository;
    }

    public List<Contract> getContracts(){
        return contractRepository.findAll();
    }

    public Contract create(Contract contract){
        Contract cont=getByUserId(contract.getUserId());
        if(cont==null){
            return  contractRepository.save(contract);
        }

        cont.setCompanyId(contract.getCompanyId());
        cont.setEquipments(contract.getEquipments());
        cont.setQuantity(contract.getQuantity());
        cont.setUserId(contract.getUserId());
        cont.setDateTimeInMS(contract.getDateTimeInMS());
        contractRepository.save(cont);
        return cont;

    }
    public Contract getByUserId(Long userId){
        return contractRepository.findByUserId(userId);
    }

    public List<ContractDto> getByCompanyId(Long companyId){
        List<Contract> contracts= contractRepository.findAllByCompanyId(companyId);
        List<Equipment> equipments=equipmentRepository.findEquipmentByCompanyId(companyId);
        List<ContractDto> contractsDto=new ArrayList<>();
        for (var contract:contracts) {
            User user=userRepository.getUserById(contract.getUserId());
            List<Equipment> equipmentsDto=new ArrayList<>();
            for (var equipmentId:contract.getEquipments()) {
                var item=equipments.stream().filter(equipment -> equipment.getId() == equipmentId).findFirst();
                if(item.isEmpty())continue;
                equipmentsDto.add(item.get());
            }

            ContractDto dto=new ContractDto(contract.getDateTimeInMS(),equipmentsDto,contract.getQuantity(),user.getUsername());
            contractsDto.add(dto);
        }

        return contractsDto;
    }

    public void deleteByUserId(Long userId){
        contractRepository.deleteById(userId);
    }
}
