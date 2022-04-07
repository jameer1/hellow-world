package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpProvidentFundContributionTO;
import com.rjtech.register.emp.dto.EmpProvidentFundDetailTO;
import com.rjtech.register.emp.dto.EmpProvidentFundTO;
import com.rjtech.register.emp.dto.EmpProvidentFundTaxTO;
import com.rjtech.common.dto.SuperProvidentFund;

public class EmpProvidentFundResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 254224135033402637L;
    private List<EmpProvidentFundTO> empProvidentFundTOs = new ArrayList<EmpProvidentFundTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    private List<EmpProvidentFundContributionTO> empProvidentFundContributionTOs = new ArrayList<EmpProvidentFundContributionTO>();
    private List<EmpProvidentFundDetailTO> empProvidentFundDetailTOs = new ArrayList<EmpProvidentFundDetailTO>();
    private List<EmpProvidentFundTaxTO> empProvidentFundTaxTOs = new ArrayList<EmpProvidentFundTaxTO>();
    private Map<Long, SuperProvidentFund> providentFundCodeMap = new HashMap<Long, SuperProvidentFund>();

    private LabelKeyTO projGeneralLabelTO = new LabelKeyTO();
    //private Map<Long, LabelKeyTO> providentFundCodeMap = new HashMap<Long, LabelKeyTO>();

    public List<EmpProvidentFundTO> getEmpProvidentFundTOs() {
        return empProvidentFundTOs;
    }

    public void setEmpProvidentFundTOs(List<EmpProvidentFundTO> empProvidentFundTOs) {
        this.empProvidentFundTOs = empProvidentFundTOs;
    }

    public List<EmpProvidentFundContributionTO> getEmpProvidentFundContributionTOs() {
        return empProvidentFundContributionTOs;
    }

    public void setEmpProvidentFundContributionTOs(
            List<EmpProvidentFundContributionTO> empProvidentFundContributionTOs) {
        this.empProvidentFundContributionTOs = empProvidentFundContributionTOs;
    }

    public List<EmpProvidentFundDetailTO> getEmpProvidentFundDetailTOs() {
        return empProvidentFundDetailTOs;
    }

    public void setEmpProvidentFundDetailTOs(List<EmpProvidentFundDetailTO> empProvidentFundDetailTOs) {
        this.empProvidentFundDetailTOs = empProvidentFundDetailTOs;
    }

    public LabelKeyTO getProjGeneralLabelTO() {
        return projGeneralLabelTO;
    }

    public void setProjGeneralLabelTO(LabelKeyTO projGeneralLabelTO) {
        this.projGeneralLabelTO = projGeneralLabelTO;
    }

    public Map<Long, SuperProvidentFund> getProvidentFundCodeMap() {
        return providentFundCodeMap;
    }

    public void setProvidentFundCodeMap(Map<Long, SuperProvidentFund> providentFundCodeMap) {
        this.providentFundCodeMap = providentFundCodeMap;
    }

    public List<EmpProvidentFundTaxTO> getEmpProvidentFundTaxTOs() {
        return empProvidentFundTaxTOs;
    }

    public void setEmpProvidentFundTaxTOs(List<EmpProvidentFundTaxTO> empProvidentFundTaxTOs) {
        this.empProvidentFundTaxTOs = empProvidentFundTaxTOs;
    }

}
