package com.rjtech.timemanagement.workdairy.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyProgressDtlTO;
import com.rjtech.timemanagement.workdairy.dto.WorkDairyTO;

public class WorkDairyProgressSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;
    
    private List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs = new ArrayList<WorkDairyProgressDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq = new WorkDairyCostCodeSaveReq();

    private WorkDairyTO workDairyTO = new WorkDairyTO();
    private String folderCategory;
    private Long userId;

    public WorkDairyTO getWorkDairyTO() {
        return workDairyTO;
    }

    public void setWorkDairyTO(WorkDairyTO workDairyTO) {
        this.workDairyTO = workDairyTO;
    }

    public List<WorkDairyProgressDtlTO> getWorkDairyProgressDtlTOs() {
        return workDairyProgressDtlTOs;
    }

    public void setWorkDairyProgressDtlTOs(List<WorkDairyProgressDtlTO> workDairyProgressDtlTOs) {
        this.workDairyProgressDtlTOs = workDairyProgressDtlTOs;
    }

    public WorkDairyCostCodeSaveReq getWorkDairyCostCodeSaveReq() {
        return workDairyCostCodeSaveReq;
    }

    public void setWorkDairyCostCodeSaveReq(WorkDairyCostCodeSaveReq workDairyCostCodeSaveReq) {
        this.workDairyCostCodeSaveReq = workDairyCostCodeSaveReq;
    }
    
    public String getFolderCategory() {
    	return folderCategory;
    }
    
    public void setFolderCategory( String folderCategory ) {
    	this.folderCategory = folderCategory;
    }
    
    public Long getUserId() {
    	return userId;
    }
    
    public void setUserId( Long userId ) {
    	this.userId = userId;
    }
    
    public String toString() {
    	return "WorkDairyTo values:"+workDairyTO.getCrewId()+" projID:"+workDairyTO.getId();
    }
}
