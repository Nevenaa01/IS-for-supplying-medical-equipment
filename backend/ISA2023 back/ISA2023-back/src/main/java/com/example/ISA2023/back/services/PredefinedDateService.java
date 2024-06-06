package com.example.ISA2023.back.services;

import com.example.ISA2023.back.models.PredefinedDate;
import com.example.ISA2023.back.models.ReservedDate;
import com.example.ISA2023.back.models.irepositories.CompanyRepository;
import com.example.ISA2023.back.models.irepositories.IPredefinedDateRepository;
import com.example.ISA2023.back.models.irepositories.IReservedDateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredefinedDateService {
    private final IPredefinedDateRepository predefinedDateRepository;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final IReservedDateRepository reservedDateRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public PredefinedDateService(IPredefinedDateRepository predefinedDateRepository, CompanyService companyService, CompanyRepository companyRepository, IReservedDateRepository reservedDateRepository) {
        this.predefinedDateRepository = predefinedDateRepository;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
        this.reservedDateRepository = reservedDateRepository;
    }

    public List<PredefinedDate> getAll(){
        return predefinedDateRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public PredefinedDate create(PredefinedDate predefinedDate, long companyId){
        List<PredefinedDate> predefinedDates = predefinedDateRepository.findAllTransactional();
        List<ReservedDate> reservedDates = reservedDateRepository.findAllByCompanyIdTransactional(companyId);

        if(!predefinedDates.stream().filter(d -> d.getDateTimeInMs().equals(predefinedDate.getDateTimeInMs())).toList().isEmpty()){
            return null;
        }
        if(!reservedDates.stream().filter(d -> d.getDateTimeInMS().equals(predefinedDate.getDateTimeInMs())).toList().isEmpty()){
            return null;
        }

        var company = companyRepository.getByIdTransactional(companyId);
        var savedPredefinedDate = predefinedDateRepository.save(predefinedDate);

        List<Long> predefineDatesId = company.getPredefinedDatesId();
        predefineDatesId.add(savedPredefinedDate.getId());
        company.setPredefinedDatesId(predefineDatesId);

        companyService.update(companyId, company);
        return savedPredefinedDate;
    }

    public void deleteById(long id, long companyId){
        predefinedDateRepository.deleteById(id);

        var company = companyService.getById(companyId);
        List<Long> predefineDatesId = company.getPredefinedDatesId();

        company.setPredefinedDatesId(predefineDatesId
                                        .stream().filter(p -> !p.equals(id)).toList());

        companyService.update(companyId, company);
    }

    public List<PredefinedDate> findAllById(List<Long> listId){
        List<PredefinedDate> dates=new ArrayList<>();
        for (Long id:listId) {
            dates.add(predefinedDateRepository.findById(id).get());
        }
        return  dates;
    }
    public PredefinedDate update(PredefinedDate dateToUpdate){
        return predefinedDateRepository.save(dateToUpdate);
    }
}
