package com.rjtech.register.plant.req;

import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class PlantReqForTransGetReq extends ProjectTO {

    private static final long serialVersionUID = 8502103901363443262L;
    private long plantId;
    private List<String> apprStatus;
    private long reqEmpId;
    private Long purchaseTypeId;
    private String fromDate;
    private String toDate;

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public List<String> getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(List<String> apprStatus) {
        this.apprStatus = apprStatus;
    }

    public long getReqEmpId() {
        return reqEmpId;
    }

    public void setReqEmpId(long reqEmpId) {
        this.reqEmpId = reqEmpId;
    }

    public Long getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(Long purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
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

}
