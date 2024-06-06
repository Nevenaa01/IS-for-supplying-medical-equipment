package com.example.ISA2023.back.models;

import jakarta.persistence.*;

@Entity
@Table
public class PredefinedDate {
    @Id
    @SequenceGenerator(
            name="predifiend_date_sequence",
            sequenceName = "predifiend_date_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "predifiend_date_sequence"
    )
    private Long id;
    private Long companyAdminId;
    private Long dateTimeInMs;
    private Long duration;
    private boolean isFree;

    public PredefinedDate() {
    }

    public PredefinedDate(Long id, Long companyAdminId, Long dateTimeInMs, Long duration,boolean isFree) {
        this.id = id;
        this.companyAdminId = companyAdminId;
        this.dateTimeInMs = dateTimeInMs;
        this.duration = duration;
        this.isFree=isFree;
    }

    public PredefinedDate(Long companyAdminId, Long dateTimeInMs, Long duration,boolean isFree) {
        this.companyAdminId = companyAdminId;
        this.dateTimeInMs = dateTimeInMs;
        this.duration = duration;
        this.isFree=isFree;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        this.isFree = free;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyAdminId() {
        return companyAdminId;
    }

    public void setCompanyAdminId(Long companyAdminId) {
        this.companyAdminId = companyAdminId;
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
}
