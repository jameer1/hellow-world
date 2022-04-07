package com.rjtech.common.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

public class EPSProjectTO extends ClientTO {

    private static final long serialVersionUID = -8303174681725241643L;

    private Long projId;
    private String projCode;
    private String projName;
    private String projType;
    private boolean expand = false;
    private boolean proj;
    private Long parentId;
    private String parentName;
    private String parentCode;
    private String startDate;
    private String finishDate;
    private boolean usrProj;
    private boolean assignedStatus;
    private boolean isEnableContractType;
    private EPSProjectTO parentProjectTO;
    private List<EPSProjectTO> childProjs = new ArrayList<EPSProjectTO>();
    private ProjGeneralMstrTO projGeneralMstrTO;
    private boolean projectAssigned;
    private String projCurrentPhase;
    private Long earnedValue;
    private BigDecimal plannedValue;


    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public boolean isProj() {
        return proj;
    }

    public void setProj(boolean proj) {
        this.proj = proj;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isUsrProj() {
        return usrProj;
    }

    public void setUsrProj(boolean usrProj) {
        this.usrProj = usrProj;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public List<EPSProjectTO> getChildProjs() {
        return childProjs;
    }
    
    public boolean isProjectAssigned() {
        return projectAssigned;
    }

    public void setProjectAssigned(boolean projectAssigned) {
        this.projectAssigned = projectAssigned;
    }

    public void setChildProjs(List<EPSProjectTO> childProjs) {
        this.childProjs = childProjs;
    }

    public boolean isAssignedStatus() {
        return assignedStatus;
    }

    public void setAssignedStatus(boolean assignedStatus) {
        this.assignedStatus = assignedStatus;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public boolean isEnableContractType() {
        return isEnableContractType;
    }

    public void setEnableContractType(boolean enableContractType) {
        isEnableContractType = enableContractType;
    }

    public String getProjCurrentPhase() {
        return projCurrentPhase;
    }

    public void setProjCurrentPhase(String projCurrentPhase) {
        this.projCurrentPhase = projCurrentPhase;
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projId == null) ? 0 : projId.hashCode());
        return result;
    }

    public EPSProjectTO getParentProjectTO() {
        return parentProjectTO;
    }

    public void setParentProjectTO(EPSProjectTO parentProjectTO) {
        this.parentProjectTO = parentProjectTO;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EPSProjectTO other = (EPSProjectTO) obj;
        if (projId == null) {
            if (other.projId != null)
                return false;
        } else if (!projId.equals(other.projId))
            return false;
        return true;
    }

	public ProjGeneralMstrTO getProjGeneralMstrTO() {
		return projGeneralMstrTO;
	}

	public void setProjGeneralMstrTO(ProjGeneralMstrTO projGeneralMstrTO) {
		this.projGeneralMstrTO = projGeneralMstrTO;
	}
	
    public Long getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(Long earnedValue) {
        this.earnedValue = earnedValue;
    }

    public BigDecimal getPlannedValue() {
        return plannedValue;
    }

    public void setPlannedValue(BigDecimal plannedValue) {
        this.plannedValue = plannedValue;
    }

}
