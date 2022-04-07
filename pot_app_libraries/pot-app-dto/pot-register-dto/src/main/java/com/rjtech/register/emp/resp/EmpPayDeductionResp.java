package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpPayDeductionDetailTO;
import com.rjtech.register.emp.dto.EmpPayDeductionQuestionTO;
import com.rjtech.register.emp.dto.EmpPayDeductionTO;
import com.rjtech.common.dto.PayDeductionCodes;

public class EmpPayDeductionResp extends AppResp {

    private static final long serialVersionUID = 8545628356164980522L;

    private List<EmpPayDeductionTO> empPayDeductionTOs = new ArrayList<EmpPayDeductionTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<EmpPayDeductionDetailTO> empPayDeductionDetailTOs = new ArrayList<EmpPayDeductionDetailTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private List<EmpPayDeductionQuestionTO> empPayDeductionQuestionTOs = new ArrayList<EmpPayDeductionQuestionTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private LabelKeyTO projGeneralLabelTO = new LabelKeyTO();
    //private Map<Long, LabelKeyTO> payDeductionCodeMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, PayDeductionCodes> payDeductionCodeMap = new HashMap<Long, PayDeductionCodes>();

    public LabelKeyTO getProjGeneralLabelTO() {
        return projGeneralLabelTO;
    }

    public List<EmpPayDeductionTO> getEmpPayDeductionTOs() {
        return empPayDeductionTOs;
    }

    public void setEmpPayDeductionTOs(List<EmpPayDeductionTO> empPayDeductionTOs) {
        this.empPayDeductionTOs = empPayDeductionTOs;
    }

    public List<EmpPayDeductionDetailTO> getEmpPayDeductionDetailTOs() {
        return empPayDeductionDetailTOs;
    }

    public void setEmpPayDeductionDetailTOs(List<EmpPayDeductionDetailTO> empPayDeductionDetailTOs) {
        this.empPayDeductionDetailTOs = empPayDeductionDetailTOs;
    }

    public List<EmpPayDeductionQuestionTO> getEmpPayDeductionQuestionTOs() {
        return empPayDeductionQuestionTOs;
    }

    public void setEmpPayDeductionQuestionTOs(List<EmpPayDeductionQuestionTO> empPayDeductionQuestionTOs) {
        this.empPayDeductionQuestionTOs = empPayDeductionQuestionTOs;
    }

    public void setProjGeneralLabelTO(LabelKeyTO projGeneralLabelTO) {
        this.projGeneralLabelTO = projGeneralLabelTO;
    }

    public Map<Long, PayDeductionCodes> getPayDeductionCodeMap() {
        return payDeductionCodeMap;
    }

    public void setPayDeductionCodeMap(Map<Long, PayDeductionCodes> payDeductionCodeMap) {
        this.payDeductionCodeMap = payDeductionCodeMap;
    }

}
