package com.example.ISA2023.back.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Entity
@Table
public class Company {
    @Id
    @SequenceGenerator(
            name="company_sequence",
            sequenceName = "company_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "company_sequence"
    )
    private Long id;
    private String name;
    private String address;
    private String description;
    private Double avgGrade;
    private List<Long> predefinedDatesId;
    private List<Long> administratorId;


    public Company() {
    }

    public Company(Long id, String name, String address, String description, Double avgGrade, List<Long> predefinedDatesId, List<Long> administratorId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.avgGrade = avgGrade;
        this.predefinedDatesId = predefinedDatesId;
        this.administratorId = administratorId;
    }

    public Company(String name, String address, String description, Double avgGrade, List<Long> predefinedDatesId, List<Long> administratorId) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.avgGrade = avgGrade;
        this.predefinedDatesId = predefinedDatesId;
        this.administratorId = administratorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public List<Long> getPredefinedDatesId() {
        return predefinedDatesId;
    }

    public void setPredefinedDatesId(List<Long> predefinedDatesId) {
        this.predefinedDatesId = predefinedDatesId;
    }

    public List<Long> getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(List<Long> administratorId) {
        this.administratorId = administratorId;
    }
}
