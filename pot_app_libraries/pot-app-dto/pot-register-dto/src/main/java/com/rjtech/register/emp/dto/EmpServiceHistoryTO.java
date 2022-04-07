package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpServiceHistoryTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -2669918280548370303L;

    private Long id;
    private Long projPostingId;
    private Long empRegId;
    private Long projEmpRegId;
    private Long empServiceHistoryId;
    private Long empClassId;
    private String actualMobDate;
    private String expectDeMobDate;
    private String actualDeMobDate;
    private String responsibilities;
    private String demobStatus;
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjPostingId() {
        return projPostingId;
    }

    public void setProjPostingId(Long projPostingId) {
        this.projPostingId = projPostingId;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public Long getProjEmpRegId() {
        return projEmpRegId;
    }

    public void setProjEmpRegId(Long projEmpRegId) {
        this.projEmpRegId = projEmpRegId;
    }

    public Long getEmpServiceHistoryId() {
        return empServiceHistoryId;
    }

    public void setEmpServiceHistoryId(Long empServiceHistoryId) {
        this.empServiceHistoryId = empServiceHistoryId;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId(Long empClassId) {
        this.empClassId = empClassId;
    }

    public String getActualMobDate() {
        return actualMobDate;
    }

    public void setActualMobDate(String actualMobDate) {
        this.actualMobDate = actualMobDate;
    }

    public String getExpectDeMobDate() {
        return expectDeMobDate;
    }

    public void setExpectDeMobDate(String expectDeMobDate) {
        this.expectDeMobDate = expectDeMobDate;
    }

    public String getActualDeMobDate() {
        return actualDeMobDate;
    }

    public void setActualDeMobDate(String actualDeMobDate) {
        this.actualDeMobDate = actualDeMobDate;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getDemobStatus() {
        return demobStatus;
    }

    public void setDemobStatus(String demobStatus) {
        this.demobStatus = demobStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
