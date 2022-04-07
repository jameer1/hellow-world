package com.rjtech.projectlib.resp;

import java.io.Serializable;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;

public class ProjectLibOnLoadResp extends AppResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Map<Long, LabelKeyTO> userProjMap = null;

    private Map<Long, LabelKeyTO> projClassMap = null;

    private Map<Long, LabelKeyTO> projCostItemMap = null;

    public ProjectLibOnLoadResp() {

    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public Map<Long, LabelKeyTO> getProjCostItemMap() {
        return projCostItemMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getProjClassMap() {
        return projClassMap;
    }

    public void setProjClassMap(Map<Long, LabelKeyTO> projClassMap) {
        this.projClassMap = projClassMap;
    }

    public void setProjCostItemMap(Map<Long, LabelKeyTO> projCostItemMap) {
        this.projCostItemMap = projCostItemMap;
    }

}
