package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractCmpTO;

public class PreContractCmpSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<PreContractCmpTO> preContractCmpTOs = new ArrayList<PreContractCmpTO>();
    private Long preContractId;
    private String closeDate;
    private String revisedCloseDate;
    private Long reqUsrId;
    private Long apprUsrId;
    private Integer apprstatus;

    public List<PreContractCmpTO> getPreContractCmpTOs() {
        return preContractCmpTOs;
    }

    public void setPreContractCmpTOs(List<PreContractCmpTO> preContractCmpTOs) {
        this.preContractCmpTOs = preContractCmpTOs;
    }

    public Long getReqUsrId() {
        return reqUsrId;
    }

    public void setReqUsrId(Long reqUsrId) {
        this.reqUsrId = reqUsrId;
    }

    public Long getApprUsrId() {
        return apprUsrId;
    }

    public void setApprUsrId(Long apprUsrId) {
        this.apprUsrId = apprUsrId;
    }

    public Long getPreContractId() {
        return preContractId;
    }

    public void setPreContractId(Long preContractId) {
        this.preContractId = preContractId;
    }

    public Integer getApprstatus() {
        return apprstatus;
    }

    public void setApprstatus(Integer apprstatus) {
        this.apprstatus = apprstatus;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getRevisedCloseDate() {
        return revisedCloseDate;
    }

    public void setRevisedCloseDate(String revisedCloseDate) {
        this.revisedCloseDate = revisedCloseDate;
    }

}
