package com.rjtech.register.material.resp;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.centrallib.dto.RegisterOnLoadTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public abstract class MaterialOnLoadResp extends AppResp {

    private static final long serialVersionUID = -1467464889111734314L;

    private RegisterOnLoadTO registerOnLoadTO;
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
    private String fromDate;
    private String toDate;

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public RegisterOnLoadTO getRegisterOnLoadTO() {
        return registerOnLoadTO;
    }

    public void setRegisterOnLoadTO(RegisterOnLoadTO registerOnLoadTO) {
        this.registerOnLoadTO = registerOnLoadTO;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
