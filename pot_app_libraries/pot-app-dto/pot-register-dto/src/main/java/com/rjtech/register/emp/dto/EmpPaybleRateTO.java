package com.rjtech.register.emp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class EmpPaybleRateTO extends ProjectTO {

    private static final long serialVersionUID = 4768660206431955600L;

    private Long id;
    private Long empProjId;
    private Long empRegId;
    private BigDecimal basicPay;
    private Long projGenerealId;
    private String unitPayRate = "Monthly";
    private String payCycle;
    private String payRollStatus;
    private String fromDate;
    private String toDate;
    private String payRollDate;
    private boolean latest;
    private ProjEmpRegisterTO projEmpRegisterTO = new ProjEmpRegisterTO();

    private List<EmpPaybleRateDetailTO> empPaybleRateDetailTOs = new ArrayList<EmpPaybleRateDetailTO>();

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

    public Long getProjGenerealId() {
        return projGenerealId;
    }

    public void setProjGenerealId(Long projGenerealId) {
        this.projGenerealId = projGenerealId;
    }

    public String getPayRollDate() {
        return payRollDate;
    }

    public void setPayRollDate(String payRollDate) {
        this.payRollDate = payRollDate;
    }

    public String getUnitPayRate() {
        return unitPayRate;
    }

    public void setUnitPayRate(String unitPayRate) {
        this.unitPayRate = unitPayRate;
    }

    public String getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(String payCycle) {
        this.payCycle = payCycle;
    }

    public BigDecimal getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(BigDecimal basicPay) {
        this.basicPay = basicPay;
    }

    public String getPayRollStatus() {
        return payRollStatus;
    }

    public void setPayRollStatus(String payRollStatus) {
        this.payRollStatus = payRollStatus;
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

    public Long getEmpProjId() {
        return empProjId;
    }

    public void setEmpProjId(Long empProjId) {
        this.empProjId = empProjId;
    }

    public ProjEmpRegisterTO getProjEmpRegisterTO() {
        return projEmpRegisterTO;
    }

    public void setProjEmpRegisterTO(ProjEmpRegisterTO projEmpRegisterTO) {
        this.projEmpRegisterTO = projEmpRegisterTO;
    }

    public List<EmpPaybleRateDetailTO> getEmpPaybleRateDetailTOs() {
        return empPaybleRateDetailTOs;
    }

    public void setEmpPaybleRateDetailTOs(List<EmpPaybleRateDetailTO> empPaybleRateDetailTOs) {
        this.empPaybleRateDetailTOs = empPaybleRateDetailTOs;
    }

}
