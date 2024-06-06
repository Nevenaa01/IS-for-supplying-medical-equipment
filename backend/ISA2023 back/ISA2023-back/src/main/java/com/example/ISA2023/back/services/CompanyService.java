package com.example.ISA2023.back.services;

import com.example.ISA2023.back.dtos.ReservedDatesDto;
import com.example.ISA2023.back.dtos.ReservedDatesForCalendarDto;
import com.example.ISA2023.back.models.Company;
import com.example.ISA2023.back.models.Equipment;
import com.example.ISA2023.back.models.PredefinedDate;
import com.example.ISA2023.back.models.irepositories.CompanyRepository;
import com.example.ISA2023.back.models.irepositories.IPredefinedDateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final IPredefinedDateRepository predefinedDateRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,IPredefinedDateRepository predefinedDateRepository) {
        this.companyRepository = companyRepository;
        this.predefinedDateRepository=predefinedDateRepository;
    }

    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    public Company getById(Long id){
        return companyRepository.findById(id).orElse(null);
    }

    public List<Company> getSearchedCompanies(String content,double rating){
        return companyRepository.findByAddressNameRating(content,rating);
    }

    public List<Company> getSearchedRatingCompanies(double rating) {
        return companyRepository.findByRating(rating);
    }
    public Company update(long id, Company company){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()) {
            var existingCompany = optionalCompany.get();
            modelMapper.map(company, existingCompany);
            companyRepository.save(existingCompany);
            return existingCompany;
        }

        return null;
    }
    public Company create(Company company)
    {
        Company newCompany=new Company(company.getName(), company.getAddress(), company.getDescription(), company.getAvgGrade(),company.getPredefinedDatesId(), company.getAdministratorId());
        companyRepository.save(newCompany);

        return newCompany;
    }

    public Boolean giveCompanyAdmins(Long companyId, Long adminId)
    {
        try 
        {
            var company= companyRepository.findById(companyId);
            List<Long>companyAdmins=company.get().getAdministratorId();
            companyAdmins.add(adminId);
            company.get().setAdministratorId(companyAdmins);
            companyRepository.save(company.get());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }
    public List<ReservedDatesForCalendarDto> findAllPredefinedDatesByCompanyId(Long companyId)
    {
        List<Long> list1=companyRepository.findAllPredefinedDatesByCompanyId(companyId).getPredefinedDatesId();
        List<PredefinedDate> list= predefinedDateRepository.findAllById(list1) ;
        List<ReservedDatesForCalendarDto> allDates=new ArrayList<>();
        List<String> e=new ArrayList<>();
        for (var d:list) {

            ReservedDatesForCalendarDto rd=new ReservedDatesForCalendarDto();
            rd.setId(d.getId());
            rd.setDateTimeInMS(d.getDateTimeInMs());
            rd.setDuration(d.getDuration());
            rd.setEquipments(e);
            rd.setUserName("");
            rd.setUserSurname("");
            allDates.add(rd);
        }
        return allDates;
    }

    @Cacheable("companies")
    public List<Company> getAllCached(){
        simulateSlowService();
        return companyRepository.findAll();
    }

    static private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}

