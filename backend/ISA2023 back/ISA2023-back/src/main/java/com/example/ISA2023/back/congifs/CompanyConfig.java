package com.example.ISA2023.back.congifs;

import com.example.ISA2023.back.models.Company;
import com.example.ISA2023.back.models.irepositories.CompanyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
@Configuration
public class CompanyConfig {
    @Bean
    CommandLineRunner commandLineRunner(CompanyRepository repository){
        return args -> {
            Company company1 = new Company(
                    "company1",
                    "address1",
                    "description1",
                    9.2,
                    List.of(1581289200000L),
                    List.of(1L)
            );

            Company company2 = new Company(
                    "company2",
                    "address2",
                    "description2",
                    9.2,
                    List.of(1581289200000L),
                    List.of(1L)
            );

            //repository.saveAll(List.of(company1, company2));
        };
    }
}
