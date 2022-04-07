package com.rjtech.projsettings.dto;

import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.common.dto.ProjectTO;

public class ProjPayRollCycleTO extends ProjectTO {

    private static final long serialVersionUID = -3019791445459392187L;
    private Long id;
    private String payRollType;
    private String payRollTypeValue;
    private String payRoll;

    private ProcureMentCatgTO procureMentCatgTO;
    private String projEmpCatg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayRollType() {
        return payRollType;
    }

    public void setPayRollType(String payRollType) {
        this.payRollType = payRollType;
    }

    public String getPayRollTypeValue() {
        return payRollTypeValue;
    }

    public void setPayRollTypeValue(String payRollTypeValue) {
        this.payRollTypeValue = payRollTypeValue;
    }

    public String getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(String payRoll) {
        this.payRoll = payRoll;
    }

    public ProcureMentCatgTO getProcureMentCatgTO() {
        return procureMentCatgTO;
    }

    public void setProcureMentCatgTO(ProcureMentCatgTO procureMentCatgTO) {
        this.procureMentCatgTO = procureMentCatgTO;
    }

    public String getProjEmpCatg() {
        return projEmpCatg;
    }

    public void setProjEmpCatg(String projEmpCatg) {
        this.projEmpCatg = projEmpCatg;
    }

}
