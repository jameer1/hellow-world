package com.rjtech.projsettings.dto;

import com.rjtech.common.dto.ProjectTO;

public class ProjProgressClaimApprTO extends ProjectTO {

    private static final long serialVersionUID = -2433178249873287777L;
    private Long id;
    private Long claimId;
    private Long apprUserId;
    private Long claimPeriod;

    private ProjProcurementApprTO projProcurementApprTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public Long getApprUserId() {
        return apprUserId;
    }

    public void setApprUserId(Long apprUserId) {
        this.apprUserId = apprUserId;
    }

    public Long getClaimPeriod() {
        return claimPeriod;
    }

    public void setClaimPeriod(Long claimPeriod) {
        this.claimPeriod = claimPeriod;
    }

    public ProjProcurementApprTO getProjProcurementApprTO() {
        return projProcurementApprTO;
    }

    public void setProjProcurementApprTO(ProjProcurementApprTO projProcurementApprTO) {
        this.projProcurementApprTO = projProcurementApprTO;
    }

}
