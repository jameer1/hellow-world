package com.rjtech.projectlib.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projectlib.dto.ProjWorkShiftTO;

public class ProjWorkShiftOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ProjWorkShiftTO projWorkShiftTO = null;

    public ProjWorkShiftOnLoadResp() {
        projWorkShiftTO = new ProjWorkShiftTO();
    }

    public ProjWorkShiftTO getProjWorkShiftTO() {
        return projWorkShiftTO;
    }

    public void setProjWorkShiftTO(ProjWorkShiftTO projWorkShiftTO) {
        this.projWorkShiftTO = projWorkShiftTO;
    }

}
