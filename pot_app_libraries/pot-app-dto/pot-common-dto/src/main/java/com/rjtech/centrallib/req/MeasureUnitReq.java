package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ClientTO;


public class MeasureUnitReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<MeasureUnitTO> measureUnitTOs = new ArrayList<MeasureUnitTO>(
            5);

    public List<MeasureUnitTO> getMeasureUnitTOs() {
        return measureUnitTOs;
    }

    public void setMeasureUnitTOs(List<MeasureUnitTO> measureUnitTOs) {
        this.measureUnitTOs = measureUnitTOs;
    }

}
