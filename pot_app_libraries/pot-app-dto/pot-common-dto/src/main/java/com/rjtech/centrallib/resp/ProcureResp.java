package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ProcurementTO;
import com.rjtech.common.resp.AppResp;

public class ProcureResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProcurementTO> procurementTOs = null;

    public ProcureResp() {
        procurementTOs = new ArrayList<ProcurementTO>();
    }

    public List<ProcurementTO> getProcurementTOs() {
        return procurementTOs;
    }

    public void setProcurementTOs(List<ProcurementTO> procurementTOs) {
        this.procurementTOs = procurementTOs;
    }

}
