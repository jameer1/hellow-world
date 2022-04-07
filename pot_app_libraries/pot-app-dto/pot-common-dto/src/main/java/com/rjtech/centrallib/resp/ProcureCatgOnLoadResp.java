package com.rjtech.centrallib.resp;

import com.rjtech.centrallib.dto.ProcureMentCatgTO;
import com.rjtech.common.resp.AppResp;

public class ProcureCatgOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ProcureResp procureResp = new ProcureResp();
    private ProcureMentCatgTO procureMentCatgTO = null;

    public ProcureCatgOnLoadResp() {
        procureMentCatgTO = new ProcureMentCatgTO();
    }

    public ProcureResp getProcureResp() {
        return procureResp;
    }

    public void setProcureResp(ProcureResp procureResp) {
        this.procureResp = procureResp;
    }

    public ProcureMentCatgTO getProcureMentCatgTO() {
        return procureMentCatgTO;
    }

    public void setProcureMentCatgTO(ProcureMentCatgTO procureMentCatgTO) {
        this.procureMentCatgTO = procureMentCatgTO;
    }

}
