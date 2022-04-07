package com.rjtech.projsettings.req;

public class ProjCalReq {

    private Double originalQty;
    private Double revisedQty;
    private Integer type;

    public Double getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(Double originalQty) {
        this.originalQty = originalQty;
    }

    public Double getRevisedQty() {
        return revisedQty;
    }

    public void setRevisedQty(Double revisedQty) {
        this.revisedQty = revisedQty;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
