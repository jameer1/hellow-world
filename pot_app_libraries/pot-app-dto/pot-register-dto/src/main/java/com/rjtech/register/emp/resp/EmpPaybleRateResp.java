package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpPaybleRateDetailTO;
import com.rjtech.register.emp.dto.EmpPaybleRateTO;
//import com.rjtech.centrallib.model.RegularPayAllowanceEntity;
import com.rjtech.common.dto.RegularPayAllowance;
import com.rjtech.common.dto.NonRegularPayAllowance;
import com.rjtech.common.dto.PayDeductionCodes;
public class EmpPaybleRateResp extends AppResp {

    private static final long serialVersionUID = 8545628356164980522L;

    private List<EmpPaybleRateTO> empPaybleRateTOs = new ArrayList<EmpPaybleRateTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private List<EmpPaybleRateDetailTO> empPaybleRateDetailTOs = new ArrayList<EmpPaybleRateDetailTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private LabelKeyTO projGeneralLabelTO = new LabelKeyTO();
    private Map<Long, RegularPayAllowance> regularPayCodeMap = new HashMap<Long, RegularPayAllowance>();
    private Map<Long, NonRegularPayAllowance> nonRegularPayCodeMap = new HashMap<Long, NonRegularPayAllowance>();
    private Map<Long, PayDeductionCodes> payDeductionCodeMap = new HashMap<Long, PayDeductionCodes>();

    public List<EmpPaybleRateTO> getEmpPaybleRateTOs() {
        return empPaybleRateTOs;
    }

    public void setEmpPaybleRateTOs(List<EmpPaybleRateTO> empPaybleRateTOs) {
        this.empPaybleRateTOs = empPaybleRateTOs;
    }

    public List<EmpPaybleRateDetailTO> getEmpPaybleRateDetailTOs() {
        return empPaybleRateDetailTOs;
    }

    public void setEmpPaybleRateDetailTOs(List<EmpPaybleRateDetailTO> empPaybleRateDetailTOs) {
        this.empPaybleRateDetailTOs = empPaybleRateDetailTOs;
    }

    public LabelKeyTO getProjGeneralLabelTO() {
        return projGeneralLabelTO;
    }

    public void setProjGeneralLabelTO(LabelKeyTO projGeneralLabelTO) {
        this.projGeneralLabelTO = projGeneralLabelTO;
    }
    
    public Map<Long, RegularPayAllowance> getRegularPayCodeMap() {
        return regularPayCodeMap;
    }

    public void setRegularPayCodeMap(Map<Long, RegularPayAllowance> RegularPayCodeMap) {
        this.regularPayCodeMap = regularPayCodeMap;
    }
    
    public Map<Long, NonRegularPayAllowance> getNonRegularPayCodeMap() {
        return nonRegularPayCodeMap;
    }

    public void setNonRegularPayCodeMap(Map<Long, NonRegularPayAllowance> nonRegularPayCodeMap) {
        this.nonRegularPayCodeMap = nonRegularPayCodeMap;
    }
    
    public Map<Long, PayDeductionCodes> getPayDeductionCodeMap() {
        return payDeductionCodeMap;
    }

    public void setPayDeductionCodeMap(Map<Long, PayDeductionCodes> payDeductionCodeMap) {
        this.payDeductionCodeMap = payDeductionCodeMap;
    }


}
