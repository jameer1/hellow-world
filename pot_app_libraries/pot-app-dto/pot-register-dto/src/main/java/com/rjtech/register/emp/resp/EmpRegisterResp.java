package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;
import com.rjtech.document.dto.ProjDocFileTO;
import com.rjtech.register.emp.dto.EmpDocumentsTO;

public class EmpRegisterResp extends AppResp {

    private static final long serialVersionUID = 3719597858807086400L;

    private Long projectId;
    private List<EmpRegisterDtlTO> empRegisterDtlTOs = new ArrayList<EmpRegisterDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Map<String, LabelKeyTO> empProjClassMap = new HashMap<String, LabelKeyTO>();    
    private List<ProjDocFileTO> projDocFileTOs = new ArrayList<ProjDocFileTO>();
    //private List<EmpDocumentsTO> empDocumentsTOs = new ArrayList<EmpDocumentsTO>();

    public List<EmpRegisterDtlTO> getEmpRegisterDtlTOs() {
        return empRegisterDtlTOs;
    }

    public void setEmpRegisterDtlTOs(List<EmpRegisterDtlTO> empRegisterDtlTOs) {
        this.empRegisterDtlTOs = empRegisterDtlTOs;
    }

    public Map<String, LabelKeyTO> getEmpProjClassMap() {
        return empProjClassMap;
    }

    public void setEmpProjClassMap(Map<String, LabelKeyTO> empProjClassMap) {
        this.empProjClassMap = empProjClassMap;
    }

    public void setProjectId( Long projectId ) {
    	this.projectId = projectId;
    }
    
    public Long getProjectId() {
    	return projectId;
    }
    
    public List<ProjDocFileTO> getProjDocFileTOs() {
        return projDocFileTOs;
    }

    public void setProjDocFileTOs( List<ProjDocFileTO> projDocFileTOs ) {
        this.projDocFileTOs = projDocFileTOs;
    }
    
    /*public List<EmpDocumentsTO> getEmpDocumentsTOs() {
        return empDocumentsTOs;
    }

    public void setEmpDocumentsTOs( List<EmpDocumentsTO> empDocumentsTOs ) {
        this.empDocumentsTOs = empDocumentsTOs;
    }*/
}
