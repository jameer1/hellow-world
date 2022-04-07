package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSORItemTO;

public class ProjSorOnLoadResp {
    private MeasureUnitResp measureUnitResp = new MeasureUnitResp();
    private ProjSORItemTO projSORItemTO = null;

    private List<ProjSORItemTO> projSORItemTOs = null;

    public ProjSorOnLoadResp() {
        projSORItemTO = new ProjSORItemTO();
        projSORItemTOs = new ArrayList<ProjSORItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public MeasureUnitResp getMeasureUnitResp() {
        return measureUnitResp;
    }

    public void setMeasureUnitResp(MeasureUnitResp measureUnitResp) {
        this.measureUnitResp = measureUnitResp;
    }

    public ProjSORItemTO getProjSORItemTO() {
        return projSORItemTO;
    }

    public void setProjSORItemTO(ProjSORItemTO projSORItemTO) {
        this.projSORItemTO = projSORItemTO;
    }

    public List<ProjSORItemTO> getProjSORItemTOs() {
        return projSORItemTOs;
    }

    public void setProjSORItemTOs(List<ProjSORItemTO> projSORItemTOs) {
        this.projSORItemTOs = projSORItemTOs;
    }

}
