package com.example.ISA2023.back.services;

import com.example.ISA2023.back.models.Company;
import com.example.ISA2023.back.models.Equipment;
import com.example.ISA2023.back.models.EquipmentType;
import com.example.ISA2023.back.models.irepositories.CompanyRepository;
import com.example.ISA2023.back.models.irepositories.IEquipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {
    private final IEquipmentRepository equipmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public EquipmentService(IEquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> findEquipmentByGrade(double rating)
    {
        return equipmentRepository.findEquipmentByGrade(rating);
    }
    public List<Equipment> findEquipmentByEquipmentType(EquipmentType eqtype)
    {
        return equipmentRepository.findEquipmentByEquipmentType(eqtype);
    }
    public List<Equipment> findEquipmentByName(String name)
    {
        return equipmentRepository.findEquipmentByName(name.toLowerCase());
    }
    public List<Equipment> findEquipmentByCompany(Long companyId)
    {
        return equipmentRepository.findEquipmentByCompany(companyId);
    }
    public List<Equipment> GetAllEquipment()
    {
        return equipmentRepository.GetAllEquipment();
    }
    public Equipment GetEquipmentById(Long id)
    {
        return equipmentRepository.GetEquipmentById(id);
    }
    public List<Equipment> findEquipmentByNameAndRating(String name, double rating)
    {
        return equipmentRepository.findEquipmentByNameAndRating(name,rating);
    }

    public List<Equipment> findEquipmentByCompanyId(Long id){
        return equipmentRepository.findEquipmentByCompanyId(id);
    }

    public Equipment create(Equipment equipment){
        return equipmentRepository.save(equipment);
    }

    public void delete(long id){
        equipmentRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Equipment update(long id,Equipment equipment){
        Optional<Equipment> optionalEquipment = equipmentRepository.findByIdTransactional(id);
        if(optionalEquipment.isPresent()) {
            var existingEquipment = optionalEquipment.get();
            modelMapper.map(equipment, existingEquipment);
            equipmentRepository.save(existingEquipment);
            return existingEquipment;
        }

        return null;
    }
    public Equipment lowerQuantity(Long equipmentId){
        Equipment equipment=equipmentRepository.findById(equipmentId).get();
        if(equipment.getQuantity()>=1){
            equipment.setQuantity(equipment.getQuantity()-1);
            equipmentRepository.save(equipment);
        }
        return equipment;
    }
}
