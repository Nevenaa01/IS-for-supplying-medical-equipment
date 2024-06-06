package com.example.ISA2023.back.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "\"equipment\"")
public class Equipment {
    @Id
    @SequenceGenerator(
            name = "equipment_sequence",
            sequenceName = "equipment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "equipment_sequence"
    )
    private Long id;
    private String name;
    private Double grade;
    private EquipmentType type;
    private String description;
    private Long companyId;
    private Long quantity;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Equipment(Long id, String name, Double grade, EquipmentType type, String description, Long companyId, Long quantity) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.type = type;
        this.description = description;
        this.companyId=companyId;
        this.quantity=quantity;
    }

    public Equipment(String name, Double grade, EquipmentType type, String description, Long companyId, Long quantity) {
        this.name = name;
        this.grade = grade;
        this.type = type;
        this.description = description;
        this.companyId=companyId;
        this.quantity=quantity;
    }
    public Equipment(){}
}
