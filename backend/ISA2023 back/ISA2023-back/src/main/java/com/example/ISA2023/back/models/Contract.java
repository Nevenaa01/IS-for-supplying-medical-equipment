package com.example.ISA2023.back.models;

import jakarta.persistence.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Table
public class Contract {
    @Id
    @SequenceGenerator(
            name = "contract_sequence",
            sequenceName = "contract_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contract_sequence"
    )
    private Long id;
    private Long companyId;
    private Long userId;
    private Long dateTimeInMS;
    private List<Long> equipments;
    private List<Long> quantity;

    public Contract() {
    }

    public Contract(Long id, Long companyId, Long userId, Long dateTimeInMS, List<Long> equipments, List<Long> quantity) {
        this.id = id;
        this.companyId = companyId;
        this.userId = userId;
        this.dateTimeInMS = dateTimeInMS;
        this.equipments = equipments;
        this.quantity = quantity;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDateTimeInMS() {
        return dateTimeInMS;
    }

    public void setDateTimeInMS(Long dateTimeInMS) {
        this.dateTimeInMS = dateTimeInMS;
    }

    public List<Long> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Long> equipments) {
        this.equipments = equipments;
    }

    public List<Long> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Long> quantity) {
        this.quantity = quantity;
    }
}
