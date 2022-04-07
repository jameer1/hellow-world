package com.rjtech.register.manpower.reports.dto;

import java.io.Serializable;

public class ManPowerGenderStatisticsEmpDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String gender;
    private boolean localEmployee;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isLocalEmployee() {
        return localEmployee;
    }

    public void setLocalEmployee(boolean localEmployee) {
        this.localEmployee = localEmployee;
    }

}
