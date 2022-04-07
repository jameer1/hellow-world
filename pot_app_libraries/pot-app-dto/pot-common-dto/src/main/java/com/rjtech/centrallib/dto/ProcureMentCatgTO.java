package com.rjtech.centrallib.dto;

import com.rjtech.common.dto.ClientTO;

public class ProcureMentCatgTO extends ClientTO {

    private static final long serialVersionUID = 2950084862079755848L;
    private Long proCatgId;
    private String code;
    private String desc;
    private String procureId;
    private boolean pcAssigned;

    private ProcurementTO procurement;

    public Long getProCatgId() {
        return proCatgId;
    }

    public void setProCatgId(Long proCatgId) {
        this.proCatgId = proCatgId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProcureId() {
        return procureId;
    }

    public void setProcureId(String procureId) {
        this.procureId = procureId;
    }

    public ProcurementTO getProcurement() {
        return procurement;
    }

    public void setProcurement(ProcurementTO procurement) {
        this.procurement = procurement;
    }
    
    public boolean isPcAssigned() {
        return pcAssigned;
    }

    public void setPcAssigned(boolean pcAssigned) {
        this.pcAssigned = pcAssigned;
    }
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((proCatgId == null) ? 0 : proCatgId.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProcureMentCatgTO other = (ProcureMentCatgTO) obj;
        if (proCatgId == null) {
            if (other.proCatgId != null)
                return false;
        } else if (!proCatgId.equals(other.proCatgId))
            return false;
        return true;
    }

}