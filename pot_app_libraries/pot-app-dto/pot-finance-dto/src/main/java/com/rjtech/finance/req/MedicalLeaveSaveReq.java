package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.MedicalLeaveTaxTO;

public class MedicalLeaveSaveReq extends ClientTO {

    private static final long serialVersionUID = -737452512034289082L;

    private List<MedicalLeaveTaxTO> medicalLeaveTaxTOs = new ArrayList<MedicalLeaveTaxTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<MedicalLeaveTaxTO> getMedicalLeaveTaxTOs() {
        return medicalLeaveTaxTOs;
    }

    public void setMedicalLeaveTaxTOs(List<MedicalLeaveTaxTO> medicalLeaveTaxTOs) {
        this.medicalLeaveTaxTOs = medicalLeaveTaxTOs;
    }

}
