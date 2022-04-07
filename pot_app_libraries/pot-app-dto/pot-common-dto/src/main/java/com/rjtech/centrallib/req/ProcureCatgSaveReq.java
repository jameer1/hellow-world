package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.common.dto.ClientTO;


public class ProcureCatgSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProcureMentCatgTO> procureMentCatgTOs = new ArrayList<ProcureMentCatgTO>(
            5);

    public List<ProcureMentCatgTO> getProcureMentCatgTOs() {
        return procureMentCatgTOs;
    }

    public void setProcureMentCatgTOs(List<ProcureMentCatgTO> procureMentCatgTOs) {
        this.procureMentCatgTOs = procureMentCatgTOs;
    }

}
