package com.example.ISA2023.back.dtos;

import com.example.ISA2023.back.models.QRCodeStatus;

public class ReservedDatesDto {
    private Long id;
    private Long duration;
    private Long dateTimeInMS;
    private String companyName;
    private Long companyAdminId;

    private String linkToOrder;
    private QRCodeStatus qrCodeStatus;

    public ReservedDatesDto(Long id,Long duration, Long dateTimeInMS, String companyName,Long companyAdminId,String linkToOrder,QRCodeStatus qrCodeStatus) {
        this.duration = duration;
        this.dateTimeInMS = dateTimeInMS;
        this.companyName = companyName;
        this.id=id;
        this.companyAdminId=companyAdminId;
        this.linkToOrder=linkToOrder;
        this.qrCodeStatus=qrCodeStatus;
    }

    public QRCodeStatus getQrCodeStatus() {
        return qrCodeStatus;
    }

    public void setQrCodeStatus(QRCodeStatus qrCodeStatus) {
        this.qrCodeStatus = qrCodeStatus;
    }

    public String getLinkToOrder() {
        return linkToOrder;
    }

    public void setLinkToOrder(String linkToOrder) {
        this.linkToOrder = linkToOrder;
    }

    public Long getCompanyAdminId() {
        return companyAdminId;
    }

    public void setCompanyAdminId(Long companyAdminId) {
        this.companyAdminId = companyAdminId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDateTimeInMS() {
        return dateTimeInMS;
    }

    public void setDateTimeInMS(Long dateTimeInMS) {
        this.dateTimeInMS = dateTimeInMS;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
