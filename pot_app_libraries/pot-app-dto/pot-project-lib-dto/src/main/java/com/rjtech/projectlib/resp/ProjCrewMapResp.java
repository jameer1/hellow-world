package com.rjtech.projectlib.resp;

import java.io.Serializable;
import java.util.Map;

import com.rjtech.common.resp.AppResp;
import com.rjtech.projectlib.dto.ProjCrewTO;

public class ProjCrewMapResp extends AppResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4627247625754427309L;

    private Map<Long, ProjCrewTO> projCrewMap = null;

    public Map<Long, ProjCrewTO> getProjCrewMap() {
        return projCrewMap;
    }

    public void setProjCrewMap(Map<Long, ProjCrewTO> projCrewMap) {
        this.projCrewMap = projCrewMap;
    }

}
