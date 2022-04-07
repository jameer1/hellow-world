package com.rjtech.register.emp.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.register.emp.dto.EmpEnrollmentDtlTO;
import com.rjtech.register.emp.dto.EmpRegisterDtlTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpEnrollmentSaveReq implements Serializable {

    private static final long serialVersionUID = 1L;
    private String category;
    private Long userId;
    private Long crmId;

    public EmpEnrollmentSaveReq() {

    }

    private EmpRegisterDtlTO empRegisterDtlTO;

    private List<EmpEnrollmentDtlTO> empEnrollmentDtlTO = new ArrayList<EmpEnrollmentDtlTO>();

    public EmpRegisterDtlTO getEmpRegisterDtlTO() {
        return empRegisterDtlTO;
    }

    public List<EmpEnrollmentDtlTO> getEmpEnrollmentDtlTO() {
        return empEnrollmentDtlTO;
    }

    public void setEmpRegisterDtlTO(EmpRegisterDtlTO empRegisterDtlTO) {
        this.empRegisterDtlTO = empRegisterDtlTO;
    }

    public void setEmpEnrollmentDtlTO(List<EmpEnrollmentDtlTO> empEnrollmentDtlTO) {
        this.empEnrollmentDtlTO = empEnrollmentDtlTO;
    }
    
    public void setCategory( String category ) {
    	this.category = category;
    }
    
    public String getCategory() {
    	return category;
    }
    
    public void setUserId( Long userId ) {
    	this.userId = userId;
    }
    
    public Long getUserId() {
    	return userId;
    }
    
    public void setCrmId( Long crmId ) {
    	this.crmId = crmId;
    }
    
    public Long getCrmId() {
    	return crmId;
    }
}
