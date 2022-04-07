package com.rjtech.projectlib.resp;

import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.common.resp.AppResp;
import com.rjtech.projectlib.dto.ProjSOWItemTO;

public class ProjSowOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private MeasureUnitResp measureUnitResp = new MeasureUnitResp();
    private ProjSOWItemTO projSOWItemTO = null;

    public ProjSowOnLoadResp() {
        projSOWItemTO = new ProjSOWItemTO();
    }

    public MeasureUnitResp getMeasureUnitResp() {
        return measureUnitResp;
    }

    public void setMeasureUnitResp(MeasureUnitResp measureUnitResp) {
        this.measureUnitResp = measureUnitResp;
    }

    public ProjSOWItemTO getProjSOWItemTO() {
        return projSOWItemTO;
    }

    public void setProjSOWItemTO(ProjSOWItemTO projSOWItemTO) {
        this.projSOWItemTO = projSOWItemTO;
    }

}
