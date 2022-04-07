package com.rjtech.timemanagement.timesheet.dto;

import com.rjtech.common.dto.ProjectTO;

public class TimeSheetEmpWorkDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long empDtlId;
    private Long empWageId;
    private String wageCode;
    private Long costId;
    private String costCode;
    private String costCodeName;
    private String costCodeParent;
    private String costCodeParentName;
    private Long apprUsrId;
    private Long parentId;
    private String apprStatus;
    private String apprComments;

    private Double day1Hrs;
    private Double day2Hrs;
    private Double day3Hrs;
    private Double day4Hrs;
    private Double day5Hrs;
    private Double day6Hrs;
    private Double day7Hrs;

    private boolean day1Flag;
    private boolean day2Flag;
    private boolean day3Flag;
    private boolean day4Flag;
    private boolean day5Flag;
    private boolean day6Flag;
    private boolean day7Flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpDtlId() {
        return empDtlId;
    }

    public void setEmpDtlId(Long empDtlId) {
        this.empDtlId = empDtlId;
    }

    public Long getEmpWageId() {
        return empWageId;
    }

    public void setEmpWageId(Long empWageId) {
        this.empWageId = empWageId;
    }

    public Long getApprUsrId() {
        return apprUsrId;
    }

    public void setApprUsrId(Long apprUsrId) {
        this.apprUsrId = apprUsrId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Double getDay1Hrs() {
        return day1Hrs;
    }

    public void setDay1Hrs(Double day1Hrs) {
        this.day1Hrs = day1Hrs;
    }

    public Double getDay2Hrs() {
        return day2Hrs;
    }

    public void setDay2Hrs(Double day2Hrs) {
        this.day2Hrs = day2Hrs;
    }

    public Double getDay3Hrs() {
        return day3Hrs;
    }

    public void setDay3Hrs(Double day3Hrs) {
        this.day3Hrs = day3Hrs;
    }

    public Double getDay4Hrs() {
        return day4Hrs;
    }

    public void setDay4Hrs(Double day4Hrs) {
        this.day4Hrs = day4Hrs;
    }

    public Double getDay5Hrs() {
        return day5Hrs;
    }

    public void setDay5Hrs(Double day5Hrs) {
        this.day5Hrs = day5Hrs;
    }

    public Double getDay6Hrs() {
        return day6Hrs;
    }

    public void setDay6Hrs(Double day6Hrs) {
        this.day6Hrs = day6Hrs;
    }

    public Double getDay7Hrs() {
        return day7Hrs;
    }

    public void setDay7Hrs(Double day7Hrs) {
        this.day7Hrs = day7Hrs;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

    public boolean isDay1Flag() {
        return day1Flag;
    }

    public void setDay1Flag(boolean day1Flag) {
        this.day1Flag = day1Flag;
    }

    public boolean isDay2Flag() {
        return day2Flag;
    }

    public void setDay2Flag(boolean day2Flag) {
        this.day2Flag = day2Flag;
    }

    public boolean isDay3Flag() {
        return day3Flag;
    }

    public void setDay3Flag(boolean day3Flag) {
        this.day3Flag = day3Flag;
    }

    public boolean isDay4Flag() {
        return day4Flag;
    }

    public void setDay4Flag(boolean day4Flag) {
        this.day4Flag = day4Flag;
    }

    public boolean isDay5Flag() {
        return day5Flag;
    }

    public void setDay5Flag(boolean day5Flag) {
        this.day5Flag = day5Flag;
    }

    public boolean isDay6Flag() {
        return day6Flag;
    }

    public void setDay6Flag(boolean day6Flag) {
        this.day6Flag = day6Flag;
    }

    public boolean isDay7Flag() {
        return day7Flag;
    }

    public void setDay7Flag(boolean day7Flag) {
        this.day7Flag = day7Flag;
    }

    public String getWageCode() {
        return wageCode;
    }

    public void setWageCode(String wageCode) {
        this.wageCode = wageCode;
    }

    public String getCostCode() {
        return costCode;
    }

    public void setCostCode(String costCode) {
        this.costCode = costCode;
    }

    public String getCostCodeName() {
        return costCodeName;
    }

    public void setCostCodeName(String costCodeName) {
        this.costCodeName = costCodeName;
    }

    public String getCostCodeParent() {
        return costCodeParent;
    }

    public void setCostCodeParent(String costCodeParent) {
        this.costCodeParent = costCodeParent;
    }

    public String getCostCodeParentName() {
        return costCodeParentName;
    }

    public void setCostCodeParentName(String costCodeParentName) {
        this.costCodeParentName = costCodeParentName;
    }

}