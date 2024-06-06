package com.example.ISA2023.back.dtos;

import java.util.List;

public class TrackingOrderDto {
    private Long id;
    private Long userId;
    private String userName;
    private String companyAdminName;
    private Boolean pickedUp;
    private Long dateTimeInMs;
    private Long duration;
    private List<Long> equipmentIds;
    private List<String> equipmentNames;


    public TrackingOrderDto() {
    }

    public TrackingOrderDto(Long id, Long userId, String userName, String companyAdminName, Boolean pickedUp, Long dateTimeInMs, Long duration, List<String> equipmentNames, List<Long> equipmentIds) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.companyAdminName = companyAdminName;
        this.pickedUp = pickedUp;
        this.dateTimeInMs = dateTimeInMs;
        this.duration = duration;
        this.equipmentNames = equipmentNames;
        this.equipmentIds = equipmentIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyAdminName() {
        return companyAdminName;
    }

    public void setCompanyAdminName(String companyAdminName) {
        this.companyAdminName = companyAdminName;
    }

    public Boolean getPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(Boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public Long getDateTimeInMs() {
        return dateTimeInMs;
    }

    public void setDateTimeInMs(Long dateTimeInMs) {
        this.dateTimeInMs = dateTimeInMs;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<String> getEquipmentNames() {
        return equipmentNames;
    }

    public void setEquipmentNames(List<String> equipmentNames) {
        this.equipmentNames = equipmentNames;
    }

    public List<Long> getEquipmentIds() {
        return equipmentIds;
    }

    public void setEquipmentIds(List<Long> equipmentIds) {
        this.equipmentIds = equipmentIds;
    }
}
