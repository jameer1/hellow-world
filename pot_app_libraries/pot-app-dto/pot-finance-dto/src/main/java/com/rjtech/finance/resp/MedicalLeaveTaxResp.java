package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.MedicalLeaveTaxTO;

public class MedicalLeaveTaxResp extends AppResp {

    private static final long serialVersionUID = -1187337608179037635L;

    private List<MedicalLeaveTaxTO> medicalLeaveTaxTOs = null;

    public MedicalLeaveTaxResp() {
        medicalLeaveTaxTOs = new ArrayList<MedicalLeaveTaxTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<MedicalLeaveTaxTO> getMedicalLeaveTaxTOs() {
        return medicalLeaveTaxTOs;
    }

    public void setMedicalLeaveTaxTOs(List<MedicalLeaveTaxTO> medicalLeaveTaxTOs) {
        this.medicalLeaveTaxTOs = medicalLeaveTaxTOs;
    }

}
