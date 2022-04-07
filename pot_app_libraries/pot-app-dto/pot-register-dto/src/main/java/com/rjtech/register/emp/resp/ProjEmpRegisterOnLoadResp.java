package com.rjtech.register.emp.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.emp.dto.ProjEmpRegisterTO;

public class ProjEmpRegisterOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -7767577696509757537L;

    private List<ProjEmpRegisterTO> projEmpRegisterTOs = null;
    Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();

    public ProjEmpRegisterOnLoadResp() {

        projEmpRegisterTOs = new ArrayList<ProjEmpRegisterTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public List<ProjEmpRegisterTO> getProjEmpRegisterTOs() {
        return projEmpRegisterTOs;
    }

    public void setProjEmpRegisterTOs(List<ProjEmpRegisterTO> projEmpRegisterTOs) {
        this.projEmpRegisterTOs = projEmpRegisterTOs;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

}
