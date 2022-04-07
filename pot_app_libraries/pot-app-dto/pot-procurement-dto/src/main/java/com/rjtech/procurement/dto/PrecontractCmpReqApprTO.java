package com.rjtech.procurement.dto;

import com.rjtech.common.dto.ProjectTO;

public class PrecontractCmpReqApprTO extends ProjectTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long reqUserId;
    private Long apprUserId;
    private Long contractId;
    private String apprComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(Long reqUserId) {
        this.reqUserId = reqUserId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getApprComments() {
        return apprComments;
    }

    public void setApprComments(String apprComments) {
        this.apprComments = apprComments;
    }

}
