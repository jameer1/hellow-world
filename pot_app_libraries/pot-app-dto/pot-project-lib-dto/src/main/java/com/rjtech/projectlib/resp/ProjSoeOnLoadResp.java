package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjSOEItemTO;

public class ProjSoeOnLoadResp {
    private MeasureUnitResp measureUnitResp = new MeasureUnitResp();
    private ProjSOEItemTO projSOEItemTO = null;
    private List<ProjSOEItemTO> projSOEItemTOs = null;

    public ProjSoeOnLoadResp() {
        projSOEItemTO = new ProjSOEItemTO();
        projSOEItemTOs = new ArrayList<ProjSOEItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public MeasureUnitResp getMeasureUnitResp() {
        return measureUnitResp;
    }

    public void setMeasureUnitResp(MeasureUnitResp measureUnitResp) {
        this.measureUnitResp = measureUnitResp;
    }

    public ProjSOEItemTO getProjSOEItemTO() {
        return projSOEItemTO;
    }

    public void setProjSOEItemTO(ProjSOEItemTO projSOEItemTO) {
        this.projSOEItemTO = projSOEItemTO;
    }

    public List<ProjSOEItemTO> getProjSOEItemTOs() {
        return projSOEItemTOs;
    }

    public void setProjSOEItemTOs(List<ProjSOEItemTO> projSOEItemTOs) {
        this.projSOEItemTOs = projSOEItemTOs;
    }

}
