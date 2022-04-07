package com.rjtech.timemanagement.manpower.reports.dto;

import java.util.Date;

public class CurrentEmployeeDetails extends ManPowerCostCodeDailyReportTO {

    private static final long serialVersionUID = 1L;

    private Date deMobilizationDate;
    private String phoneNum;
    private String emailId;
    private String classificationPerUnion;

    public Date getDeMobilizationDate() {
        return deMobilizationDate;
    }

    public void setDeMobilizationDate(Date deMobilizationDate) {
        this.deMobilizationDate = deMobilizationDate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getClassificationPerUnion() {
        return classificationPerUnion;
    }

    public void setClassificationPerUnion(String classificationPerUnion) {
        this.classificationPerUnion = classificationPerUnion;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        return getClass() == obj.getClass();
    }

}
