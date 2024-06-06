package com.example.ISA2023.back.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class ReservedDate {
    @Id
    @SequenceGenerator(
            name="reserved_date_sequence",
            sequenceName = "reserved_date_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reserved_date_sequence"
    )
    private Long Id;
    private Long DateTimeInMS;
    private Long userId;
    private List<Long> Equipments;
    private Long Duration;
    private Long CompanyAdminId;
    private Long CompanyId;

    private Boolean isPickedUp;
    private String linkToOrder;
    private QRCodeStatus qrCodeStatus;

    public ReservedDate() {
    }

    public ReservedDate(Long id, Long dateTimeInMS, Long userId, List<Long> equipments, Long duration, Long companyAdminId, Boolean isPickedUp,Long companyId,String linkToOrder, QRCodeStatus status) {
        Id = id;
        DateTimeInMS = dateTimeInMS;
        this.userId = userId;
        Equipments = equipments;
        Duration = duration;
        CompanyAdminId = companyAdminId;
        this.isPickedUp = isPickedUp;
        CompanyId=companyId;
        this.linkToOrder=linkToOrder;
        this.qrCodeStatus=status;

    }

    public ReservedDate(Long dateTimeInMS, Long userId, List<Long> equipments, Long duration, Long companyAdminId, Boolean isPickedUp,String linkToOrder, QRCodeStatus status) {
        DateTimeInMS = dateTimeInMS;
        this.userId = userId;
        Equipments = equipments;
        Duration = duration;
        CompanyAdminId = companyAdminId;
        this.isPickedUp = isPickedUp;
        this.linkToOrder=linkToOrder;
        this.qrCodeStatus=status;
    }

    public String getLinkToOrder() {
        return linkToOrder;
    }

    public void setLinkToOrder(String linkToOrder) {
        this.linkToOrder = linkToOrder;
    }

    public void setDuration(Long duration) {
        Duration = duration;
    }

    public void setCompanyAdminId(Long companyAdminId) {
        CompanyAdminId = companyAdminId;
    }

    public Long getDuration() {
        return Duration;
    }

    public Long getCompanyAdminId() {
        return CompanyAdminId;        
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getDateTimeInMS() {
        return DateTimeInMS;
    }

    public void setDateTimeInMS(Long dateTimeInMS) {
        DateTimeInMS = dateTimeInMS;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getEquipments() {
        return Equipments;
    }

    public void setEquipments(List<Long> equipments) {
        Equipments = equipments;
    }

    public Boolean getPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(Boolean pickedUp) {
        isPickedUp = pickedUp;
    }
    public Long getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Long companyId) {
        CompanyId = companyId;
    }

    public QRCodeStatus getQrCodeStatus() {
        return qrCodeStatus;
    }

    public void setQrCodeStatus(QRCodeStatus qrCodeStatus) {
        this.qrCodeStatus = qrCodeStatus;
    }
}
