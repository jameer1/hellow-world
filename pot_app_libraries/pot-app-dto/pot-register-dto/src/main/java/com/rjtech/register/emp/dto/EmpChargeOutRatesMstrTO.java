package com.rjtech.register.emp.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class EmpChargeOutRatesMstrTO extends ProjectTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1316562095461993744L;
    private Long id;
    private Long projLeaveCostItemId;
    private Long projMobCostItemId;
    private Long projDeMobCostItemId;
    private Long empServiceHistoryId;
    private BigDecimal normalTime;
    private BigDecimal idealTime;
    private BigDecimal paidLeave;
    private BigDecimal mobRate;
    private BigDecimal deMobRate;
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjLeaveCostItemId() {
        return projLeaveCostItemId;
    }

    public void setProjLeaveCostItemId(Long projLeaveCostItemId) {
        this.projLeaveCostItemId = projLeaveCostItemId;
    }

    public Long getProjMobCostItemId() {
        return projMobCostItemId;
    }

    public void setProjMobCostItemId(Long projMobCostItemId) {
        this.projMobCostItemId = projMobCostItemId;
    }

    public Long getProjDeMobCostItemId() {
        return projDeMobCostItemId;
    }

    public void setProjDeMobCostItemId(Long projDeMobCostItemId) {
        this.projDeMobCostItemId = projDeMobCostItemId;
    }

    public Long getEmpServiceHistoryId() {
        return empServiceHistoryId;
    }

    public void setEmpServiceHistoryId(Long empServiceHistoryId) {
        this.empServiceHistoryId = empServiceHistoryId;
    }

    public BigDecimal getNormalTime() {
        return normalTime;
    }

    public void setNormalTime(BigDecimal normalTime) {
        this.normalTime = normalTime;
    }

    public BigDecimal getIdealTime() {
        return idealTime;
    }

    public void setIdealTime(BigDecimal idealTime) {
        this.idealTime = idealTime;
    }

    public BigDecimal getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(BigDecimal paidLeave) {
        this.paidLeave = paidLeave;
    }

    public BigDecimal getMobRate() {
        return mobRate;
    }

    public void setMobRate(BigDecimal mobRate) {
        this.mobRate = mobRate;
    }

    public BigDecimal getDeMobRate() {
        return deMobRate;
    }

    public void setDeMobRate(BigDecimal deMobRate) {
        this.deMobRate = deMobRate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
