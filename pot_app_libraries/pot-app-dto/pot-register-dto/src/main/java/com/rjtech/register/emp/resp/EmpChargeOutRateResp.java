package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.EmpChargeOutRateTO;

public class EmpChargeOutRateResp extends AppResp {

    private static final long serialVersionUID = -8131250384859569760L;
    private List<EmpChargeOutRateTO> empChargeOutRateTOs = null;
    private LabelKeyTO projGeneralLabelTO = null;

    public EmpChargeOutRateResp() {
        empChargeOutRateTOs = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projGeneralLabelTO = new LabelKeyTO();
    }

    public LabelKeyTO getProjGeneralLabelTO() {
        return projGeneralLabelTO;
    }

    public void setProjGeneralLabelTO(LabelKeyTO projGeneralLabelTO) {
        this.projGeneralLabelTO = projGeneralLabelTO;
    }

    public List<EmpChargeOutRateTO> getEmpChargeOutRateTOs() {
        return empChargeOutRateTOs;
    }

    public void setEmpChargeOutRateTOs(List<EmpChargeOutRateTO> empChargeOutRateTOs) {
        this.empChargeOutRateTOs = empChargeOutRateTOs;
    }

}
