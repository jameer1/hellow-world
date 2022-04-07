package com.rjtech.register.emp.req;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpQualificationRecordsTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpQualificationRecordsSaveReq extends ProjectTO {

    private static final long serialVersionUID = 8576360441333689873L;
    private List<EmpQualificationRecordsTO> empQualificationRecordsTOs = new ArrayList<EmpQualificationRecordsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long empId;
    private String folderCategory;
    private Long userId;
    private Long projectId;

    public List<EmpQualificationRecordsTO> getEmpQualificationRecordsTOs() {
        return empQualificationRecordsTOs;
    }

    public void setEmpQualificationRecordsTOs( List<EmpQualificationRecordsTO> empQualificationRecordsTOs ) {
        this.empQualificationRecordsTOs = empQualificationRecordsTOs;
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
    
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId( Long projectId ) {
        this.projectId = projectId;
    }
    
    public String toString() {
    	return "userId:"+userId+"empId:"+empId+"folderCategory:"+folderCategory;
    }
}
