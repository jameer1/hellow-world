package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.resp.AppResp;


public class MeasureUnitResp extends AppResp {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<MeasureUnitTO> measureUnitTOs = null;

    public MeasureUnitResp() {
        measureUnitTOs = new ArrayList<MeasureUnitTO>(5);
    }

    public List<MeasureUnitTO> getMeasureUnitTOs() {
        return measureUnitTOs;
    }

}
