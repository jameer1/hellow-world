package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpQualificationRecordsTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpQualificationRecordsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -4618349203248631145L;
    private List<EmpQualificationRecordsTO> empQualificationRecordsTOs = new ArrayList<EmpQualificationRecordsTO>();

    public List<EmpQualificationRecordsTO> getEmpQualificationRecordsTOs() {
        return empQualificationRecordsTOs;
    }

    public void setEmpQualificationRecordsTOs( List<EmpQualificationRecordsTO> empQualificationRecordsTOs ) {
        this.empQualificationRecordsTOs = empQualificationRecordsTOs;
    }
    
    public String toString() {
    	return "size of empQualificationRecordsTOs is:"+empQualificationRecordsTOs.size();
    }
}
