package com.example.ISA2023.back.dtos;

import com.example.ISA2023.back.models.Equipment;

import java.util.List;

public class ContractDto {
    private Long dateTimeInMS;
    private List<Equipment> equipments;
    private List<Long> quantity;
    private String username;

    public ContractDto(Long dateTimeInMS, List<Equipment> equipments, List<Long> quantity, String username) {
        this.dateTimeInMS = dateTimeInMS;
        this.equipments = equipments;
        this.quantity = quantity;
        this.username = username;
    }

    public Long getDateTimeInMS() {
        return dateTimeInMS;
    }

    public void setDateTimeInMS(Long dateTimeInMS) {
        this.dateTimeInMS = dateTimeInMS;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }


    public List<Long> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Long> quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
