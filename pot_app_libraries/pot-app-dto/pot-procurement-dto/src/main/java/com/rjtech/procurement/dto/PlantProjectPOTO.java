package com.rjtech.procurement.dto;

import java.io.Serializable;

public class PlantProjectPOTO implements Serializable {
    /**
    	 * 
    	 */
    private static final long serialVersionUID = 1L;

    private Long poId;

    private String poName;

    private Long projId;

    private Long preContractId;

    private Long cmpId;

    private String startDate;

    private String finishDate;

    private double amount;

    private Long paymentInDays;

    public Long getPoId() {
        return poId;
    }

    public String getPoName() {
        return poName;
    }

    public Long getProjId() {
        return projId;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public Long getCmpId() {
        return cmpId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public double getAmount() {
        return amount;
    }

    public Long getPaymentInDays() {
        return paymentInDays;
    }

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public void setPoName(String poName) {
        this.poName = poName;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public void setCmpId(Long cmpId) {
        this.cmpId = cmpId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentInDays(Long paymentInDays) {
        this.paymentInDays = paymentInDays;
    }
}
