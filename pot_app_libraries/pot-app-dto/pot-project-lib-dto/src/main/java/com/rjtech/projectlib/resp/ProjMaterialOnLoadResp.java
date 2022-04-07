package com.rjtech.projectlib.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.centrallib.resp.MeasureUnitResp;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.projectlib.dto.ProjMaterialClassTO;

public class ProjMaterialOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private MeasureUnitResp measureUnitResp = new MeasureUnitResp();
    private Map<Long, LabelKeyTO> materialClassMap = new HashMap<Long, LabelKeyTO>();
    private ProjMaterialClassTO projMaterialClassTO = null;

    public ProjMaterialOnLoadResp() {
        projMaterialClassTO = new ProjMaterialClassTO();
    }

    public MeasureUnitResp getMeasureUnitResp() {
        return measureUnitResp;
    }

    public void setMeasureUnitResp(MeasureUnitResp measureUnitResp) {
        this.measureUnitResp = measureUnitResp;
    }

    public ProjMaterialClassTO getProjMaterialClassTO() {
        return projMaterialClassTO;
    }

    public void setProjMaterialClassTO(ProjMaterialClassTO projMaterialClassTO) {
        this.projMaterialClassTO = projMaterialClassTO;
    }

    public Map<Long, LabelKeyTO> getMaterialClassMap() {
        return materialClassMap;
    }

    public void setMaterialClassMap(Map<Long, LabelKeyTO> materialClassMap) {
        this.materialClassMap = materialClassMap;
    }

}
