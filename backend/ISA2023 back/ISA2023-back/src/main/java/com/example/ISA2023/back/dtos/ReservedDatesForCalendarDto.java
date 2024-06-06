package com.example.ISA2023.back.dtos;

import java.util.List;

public class ReservedDatesForCalendarDto{

    private Long Id;
    private Long DateTimeInMS;
    private String UserName;
    private String UserSurname;
    private List<String> Equipments;
    private Long Duration;

    public ReservedDatesForCalendarDto() {
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserSurname() {
        return UserSurname;
    }

    public void setUserSurname(String userSurname) {
        UserSurname = userSurname;
    }

    public List<String> getEquipments() {
        return Equipments;
    }

    public void setEquipments(List<String> equipments) {
        Equipments = equipments;
    }

    public Long getDuration() {
        return Duration;
    }

    public void setDuration(Long duration) {
        Duration = duration;
    }

}