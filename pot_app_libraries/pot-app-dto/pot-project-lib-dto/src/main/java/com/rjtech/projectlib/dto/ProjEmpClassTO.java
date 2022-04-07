package com.rjtech.projectlib.dto;

import com.rjtech.centrallib.dto.EmpClassTO;
import com.rjtech.common.dto.ClientTO;

public class ProjEmpClassTO extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private Long id;
    private Long projId;
    private String tradeContrName;
    private String unionWorkerName;
    private String projEmpCategory;
    private EmpClassTO empClassTO = new EmpClassTO();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjEmpCategory() {
        return projEmpCategory;
    }

    public void setProjEmpCategory(String projEmpCategory) {
        this.projEmpCategory = projEmpCategory;
    }

    public EmpClassTO getEmpClassTO() {
        return empClassTO;
    }

    public void setEmpClassTO(EmpClassTO empClassTO) {
        this.empClassTO = empClassTO;
    }

    public String getTradeContrName() {
        return tradeContrName;
    }

    public void setTradeContrName(String tradeContrName) {
        this.tradeContrName = tradeContrName;
    }

    public String getUnionWorkerName() {
        return unionWorkerName;
    }

    public void setUnionWorkerName(String unionWorkerName) {
        this.unionWorkerName = unionWorkerName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjEmpClassTO other = (ProjEmpClassTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
