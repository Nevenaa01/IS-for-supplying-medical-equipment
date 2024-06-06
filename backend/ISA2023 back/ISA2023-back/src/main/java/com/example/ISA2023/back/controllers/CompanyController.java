package com.example.ISA2023.back.controllers;

import com.example.ISA2023.back.dtos.ReservedDatesForCalendarDto;
import com.example.ISA2023.back.models.Company;
import com.example.ISA2023.back.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLL_USER', 'ROLL_SYSTEM_ADMIN', 'ROLL_COMPANY_ADMIN')")
    public List<Company> getAll(){return companyService.getCompanies();}
    @GetMapping
    public List<Company> getCompanies(){
        return companyService.getCompanies();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLL_USER', 'ROLL_SYSTEM_ADMIN', 'ROLL_COMPANY_ADMIN')")
    public  Company getCompany(@PathVariable Long id){
        return companyService.getById(id);
    }
    @GetMapping("/search/{content}/{rating}")
    public List<Company> getSearchedCompanies(@PathVariable String content,@PathVariable double rating){return companyService.getSearchedCompanies(content,rating);}

    @GetMapping("/searchRating/{rating}")
    public List<Company> getSearchedRatingCompanies(@PathVariable double rating){return companyService.getSearchedRatingCompanies(rating);}


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLL_COMPANY_ADMIN')")
    public Company update(@PathVariable long id, @RequestBody Company company) {return companyService.update(id, company);}
    @PostMapping
    public Company create(@RequestBody Company company)
    {
        return companyService.create(company);
    }
    @RequestMapping(value = "/addadmin/{companyId}/{adminUsername}",
            produces = "application/json",
            method=RequestMethod.PUT)
    public Boolean giveCompanyAdmins(@PathVariable Long companyId, @PathVariable Long adminId)
    {
        return companyService.giveCompanyAdmins(companyId,adminId);
    }

    @GetMapping("/allpredefineddates/{companyId}")
    public List<ReservedDatesForCalendarDto> findAllPredefinedDatesByCompanyId( @PathVariable Long companyId)
    {
        return companyService.findAllPredefinedDatesByCompanyId(companyId);
    }

}
