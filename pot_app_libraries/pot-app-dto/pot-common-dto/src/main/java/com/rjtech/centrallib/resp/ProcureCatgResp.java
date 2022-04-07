package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.common.resp.AppResp;


public class ProcureCatgResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ProcureMentCatgTO> procureMentCatgTOs = null;

    public ProcureCatgResp() {
        procureMentCatgTOs = new ArrayList<ProcureMentCatgTO>(5);
    }

    public List<ProcureMentCatgTO> getProcureMentCatgTOs() {
        return procureMentCatgTOs;
    }

    public void setProcureMentCatgTOs(List<ProcureMentCatgTO> procureMentCatgTOs) {
        this.procureMentCatgTOs = procureMentCatgTOs;
    }

}
