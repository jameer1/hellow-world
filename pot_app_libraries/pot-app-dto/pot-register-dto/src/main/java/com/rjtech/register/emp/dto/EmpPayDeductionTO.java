package com.rjtech.register.emp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class EmpPayDeductionTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long empProjId;
    private Long empRegId;
    private BigDecimal basicPay;
    private Long projGenId;
    private String unitPayRate = "Monthly";
    private String payCycle;
    private String fromDate;
    private String toDate;
    private boolean latest;

    private ProjEmpRegisterTO projEmpRegisterTO = new ProjEmpRegisterTO();
    private List<EmpPayDeductionQuestionTO> empPayDeductionQuestionTOs = new ArrayList<EmpPayDeductionQuestionTO>();
    private List<EmpPayDeductionDetailTO> empPayDeductionDetailTOs = new ArrayList<EmpPayDeductionDetailTO>();

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

    public Long getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(Long empRegId) {
        this.empRegId = empRegId;
    }

    public BigDecimal getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(BigDecimal basicPay) {
        this.basicPay = basicPay;
    }

    public Long getProjGenId() {
        return projGenId;
    }

    public void setProjGenId(Long projGenId) {
        this.projGenId = projGenId;
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

    public ProjEmpRegisterTO getProjEmpRegisterTO() {
        return projEmpRegisterTO;
    }

    public void setProjEmpRegisterTO(ProjEmpRegisterTO projEmpRegisterTO) {
        this.projEmpRegisterTO = projEmpRegisterTO;
    }

    public List<EmpPayDeductionDetailTO> getEmpPayDeductionDetailTOs() {
        return empPayDeductionDetailTOs;
    }

    public void setEmpPayDeductionDetailTOs(List<EmpPayDeductionDetailTO> empPayDeductionDetailTOs) {
        this.empPayDeductionDetailTOs = empPayDeductionDetailTOs;
    }

    public List<EmpPayDeductionQuestionTO> getEmpPayDeductionQuestionTOs() {
        return empPayDeductionQuestionTOs;
    }

    public void setEmpPayDeductionQuestionTOs(List<EmpPayDeductionQuestionTO> empPayDeductionQuestionTOs) {
        this.empPayDeductionQuestionTOs = empPayDeductionQuestionTOs;
    }

}
