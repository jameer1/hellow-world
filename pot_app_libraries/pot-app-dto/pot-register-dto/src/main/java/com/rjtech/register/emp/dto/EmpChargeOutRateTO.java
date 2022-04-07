package com.rjtech.register.emp.dto;

import java.math.BigDecimal;

import com.rjtech.common.dto.ProjectTO;

public class EmpChargeOutRateTO extends ProjectTO {

    /**
     *
     */
    private static final long serialVersionUID = -1203078091261364066L;
    private Long id;
    private Long empProjId;
    private String fromDate;
    private String toDate;
    private boolean latest;
    private Long empRegId;
    private Long measureId;
    private Long projGenId;
    private String projCurrency;
    private Long projCostItemId;

    private Long leaveCostItemId;
    private String leaveCostItemCode;
    private Long mobCostItemId;
    private String mobCostItemCode;
    private Long deMobCostItemId;
    private String deMobCostItemCode;
    private BigDecimal normalRate;
    private BigDecimal idleRate;
    private BigDecimal paidLeaveRate;
    private BigDecimal mobRate;
    private BigDecimal deMobRate;
    private String comments;

    private ProjEmpRegisterTO projEmpRegisterTO = new ProjEmpRegisterTO();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpProjId() {
        return empProjId;
    }

    public void setEmpProjId(Long empProjId) {
        this.empProjId = empProjId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public Long getProjGenId() {
        return projGenId;
    }

    public void setProjGenId(Long projGenId) {
        this.projGenId = projGenId;
    }

    public String getProjCurrency() {
        return projCurrency;
    }

    public void setProjCurrency(String projCurrency) {
        this.projCurrency = projCurrency;
    }

    public Long getProjCostItemId() {
        return projCostItemId;
    }

    public void setProjCostItemId(Long projCostItemId) {
        this.projCostItemId = projCostItemId;
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

    public String getMobCostItemCode() {
        return mobCostItemCode;
    }

    public void setMobCostItemCode(String mobCostItemCode) {
        this.mobCostItemCode = mobCostItemCode;
    }

    public String getDeMobCostItemCode() {
        return deMobCostItemCode;
    }

    public void setDeMobCostItemCode(String deMobCostItemCode) {
        this.deMobCostItemCode = deMobCostItemCode;
    }

    public Long getMobCostItemId() {
        return mobCostItemId;
    }

    public void setMobCostItemId(Long mobCostItemId) {
        this.mobCostItemId = mobCostItemId;
    }

    public Long getDeMobCostItemId() {
        return deMobCostItemId;
    }

    public void setDeMobCostItemId(Long deMobCostItemId) {
        this.deMobCostItemId = deMobCostItemId;
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

    public BigDecimal getPaidLeaveRate() {
        return paidLeaveRate;
    }

    public void setPaidLeaveRate(BigDecimal paidLeaveRate) {
        this.paidLeaveRate = paidLeaveRate;
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

    public ProjEmpRegisterTO getProjEmpRegisterTO() {
        return projEmpRegisterTO;
    }

    public void setProjEmpRegisterTO(ProjEmpRegisterTO projEmpRegisterTO) {
        this.projEmpRegisterTO = projEmpRegisterTO;
    }

}
