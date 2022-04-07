package com.rjtech.centrallib.dto;

import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class RegisterProjectLibOnLoadTO {

    private Map<Long, LabelKeyTO> userProjMap = null;

    private Map<Long, LabelKeyTO> projClassMap = null;

    private Map<Long, LabelKeyTO> projCostItemMap = null;

    public RegisterProjectLibOnLoadTO() {

    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getProjClassMap() {
        return projClassMap;
    }

    public Map<Long, LabelKeyTO> getProjCostItemMap() {
        return projCostItemMap;
    }

    public void setProjClassMap(Map<Long, LabelKeyTO> projClassMap) {
        this.projClassMap = projClassMap;
    }

    public void setProjCostItemMap(Map<Long, LabelKeyTO> projCostItemMap) {
        this.projCostItemMap = projCostItemMap;
    }

}
