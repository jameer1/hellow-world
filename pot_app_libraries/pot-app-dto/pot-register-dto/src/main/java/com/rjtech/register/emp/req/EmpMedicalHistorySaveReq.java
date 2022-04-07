package com.rjtech.register.emp.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpMedicalHistoryTO;

public class EmpMedicalHistorySaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 8576360441333689873L;
    private List<EmpMedicalHistoryTO> empMedicalHistoryTOs = new ArrayList<EmpMedicalHistoryTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long empId;
    private String folderCategory;
    private Long userId;

    public List<EmpMedicalHistoryTO> getEmpMedicalHistoryTOs() {
        return empMedicalHistoryTOs;
    }

    public void setEmpMedicalHistoryTOs(List<EmpMedicalHistoryTO> empMedicalHistoryTOs) {
        this.empMedicalHistoryTOs = empMedicalHistoryTOs;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String toString() {
    	return "userId:"+userId+"empId:"+empId+"folderCategory:"+folderCategory;
    }
}
