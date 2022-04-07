package com.rjtech.projsettings.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.ProjStatusActualTo;
import com.rjtech.common.resp.AppResp;

public class ProjStatusActualResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -1631161986565952169L;
    private Map<Long, ProjStatusActualTo> ProjStatusActualTos = new HashMap<Long, ProjStatusActualTo>();

    public Map<Long, ProjStatusActualTo> getProjStatusActualTos() {
        return ProjStatusActualTos;
    }

    public void setProjStatusActualTos(Map<Long, ProjStatusActualTo> projStatusActualTos) {
        ProjStatusActualTos = projStatusActualTos;
    }

}
