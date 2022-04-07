package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ProcurementTO;
import com.rjtech.common.dto.ClientTO;


public class ProcureSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProcurementTO> procurementTOs = new ArrayList<ProcurementTO>(
            5);

    public List<ProcurementTO> getProcurementTOs() {
        return procurementTOs;
    }

    public void setProcurementTOs(List<ProcurementTO> procurementTOs) {
        this.procurementTOs = procurementTOs;
    }

}
