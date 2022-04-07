package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;


public class MeasureDelReq extends ClientTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private List<Long> measureIds = new ArrayList<Long>(5);

    public List<Long> getMeasureIds() {
        return measureIds;
    }

    public void setMeasureIds(List<Long> measureIds) {
        this.measureIds = measureIds;
    }

}
