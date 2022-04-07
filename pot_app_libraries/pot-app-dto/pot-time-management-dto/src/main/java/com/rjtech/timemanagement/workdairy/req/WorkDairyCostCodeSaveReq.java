package com.rjtech.timemanagement.workdairy.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyCostCodeTO;

public class WorkDairyCostCodeSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private List<WorkDairyCostCodeTO> workDairyCostCodeTOs = new ArrayList<WorkDairyCostCodeTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private List<Long> deleteCostIds = new ArrayList<Long>();
    private List<Long> deleteWorkDairyCostIds = new ArrayList<Long>();

    private Long crewId;
    private Long workDairyId;
    private Long projId;

    public List<WorkDairyCostCodeTO> getWorkDairyCostCodeTOs() {
        return workDairyCostCodeTOs;
    }

    public void setWorkDairyCostCodeTOs(List<WorkDairyCostCodeTO> workDairyCostCodeTOs) {
        this.workDairyCostCodeTOs = workDairyCostCodeTOs;
    }

    public Long getCrewId() {
        return crewId;
    }

    public void setCrewId(Long crewId) {
        this.crewId = crewId;
    }

    public Long getWorkDairyId() {
        return workDairyId;
    }

    public void setWorkDairyId(Long workDairyId) {
        this.workDairyId = workDairyId;
    }

    public List<Long> getDeleteWorkDairyCostIds() {
        return deleteWorkDairyCostIds;
    }

    public void setDeleteWorkDairyCostIds(List<Long> deleteWorkDairyCostIds) {
        this.deleteWorkDairyCostIds = deleteWorkDairyCostIds;
    }

    public List<Long> getDeleteCostIds() {
        return deleteCostIds;
    }

    public void setDeleteCostIds(List<Long> deleteCostIds) {
        this.deleteCostIds = deleteCostIds;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

}
