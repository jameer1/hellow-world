package com.rjtech.projsettings.register.emp.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class EmpChargeOutRateTO extends ProjectTO {

    private static final long serialVersionUID = -1203078091261364066L;
    private Long id;
    private Long empRegId;

    private Long leaveCostItemId;
    private String leaveCostItemCode;
    private BigDecimal leaveRate;

    private Long mobCostItemId;
    private String mobCostItemCode;
    private BigDecimal mobRate;

    private Long deMobCostItemId;
    private String deMobCostItemCode;
    private BigDecimal deMobRate;

    private BigDecimal normalRate;
    private BigDecimal idleRate;

    private String currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public Long getLeaveCostItemId() {
        return leaveCostItemId;
    }

    public void setLeaveCostItemId(Long leaveCostItemId) {
        this.leaveCostItemId = leaveCostItemId;
    }

    public String getLeaveCostItemCode() {
        return leaveCostItemCode;
    }

    public void setLeaveCostItemCode(String leaveCostItemCode) {
        this.leaveCostItemCode = leaveCostItemCode;
    }

    public BigDecimal getLeaveRate() {
        return leaveRate;
    }

    public void setLeaveRate(BigDecimal leaveRate) {
        this.leaveRate = leaveRate;
    }

    public Long getMobCostItemId() {
        return mobCostItemId;
    }

    public void setMobCostItemId(Long mobCostItemId) {
        this.mobCostItemId = mobCostItemId;
    }

    public String getMobCostItemCode() {
        return mobCostItemCode;
    }

    public void setMobCostItemCode(String mobCostItemCode) {
        this.mobCostItemCode = mobCostItemCode;
    }

    public BigDecimal getMobRate() {
        return mobRate;
    }

    public void setMobRate(BigDecimal mobRate) {
        this.mobRate = mobRate;
    }

    public Long getDeMobCostItemId() {
        return deMobCostItemId;
    }

    public void setDeMobCostItemId(Long deMobCostItemId) {
        this.deMobCostItemId = deMobCostItemId;
    }

    public String getDeMobCostItemCode() {
        return deMobCostItemCode;
    }

    public void setDeMobCostItemCode(String deMobCostItemCode) {
        this.deMobCostItemCode = deMobCostItemCode;
    }

    public BigDecimal getDeMobRate() {
        return deMobRate;
    }

    public void setDeMobRate(BigDecimal deMobRate) {
        this.deMobRate = deMobRate;
    }

    public BigDecimal getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(BigDecimal normalRate) {
        this.normalRate = normalRate;
    }

    public BigDecimal getIdleRate() {
        return idleRate;
    }

    public void setIdleRate(BigDecimal idleRate) {
        this.idleRate = idleRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
