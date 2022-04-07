package com.rjtech.projectlib.resp;

import java.io.Serializable;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class UserProjEmpClassResp extends AppResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2057816392244659836L;
    private Map<Long, LabelKeyTO> projClassMap = null;

    public Map<Long, LabelKeyTO> getProjClassMap() {
        return projClassMap;
    }

    public void setProjClassMap(Map<Long, LabelKeyTO> projClassMap) {
        this.projClassMap = projClassMap;
    }

}
