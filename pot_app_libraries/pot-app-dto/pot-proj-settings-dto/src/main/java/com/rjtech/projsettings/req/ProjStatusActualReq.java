package com.rjtech.projsettings.req;

import com.rjtech.common.dto.ProjectTO;

public class ProjStatusActualReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3107240449798568802L;
    private String fromDate;
    private String toDate;
    private String contractCode;
    private Long purId;

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

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Long getPurId() {
        return purId;
    }

    public void setPurId(Long purId) {
        this.purId = purId;
    }

}
