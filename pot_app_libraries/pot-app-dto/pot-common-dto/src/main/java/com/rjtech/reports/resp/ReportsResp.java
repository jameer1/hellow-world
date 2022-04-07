package com.rjtech.reports.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;


public class ReportsResp extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -5362402028612363675L;

    private List<LabelKeyTO> labelKeyTOs = null;
    private Map<Long, LabelKeyTO> sowItemMap = null;
    private Map<Long, LabelKeyTO> costCodeItemMap = null;
    private Map<Long, LabelKeyTO> userProjMap = null;
    private Map<Long, LabelKeyTO> userEpsMap = null;
    private Map<Long, LabelKeyTO> usersMap = null;

    public ReportsResp() {
        labelKeyTOs = new ArrayList<LabelKeyTO>(5);
        sowItemMap = new HashMap<Long, LabelKeyTO>(5);
        costCodeItemMap = new HashMap<Long, LabelKeyTO>(5);
        userProjMap = new HashMap<Long, LabelKeyTO>(5);
        usersMap = new HashMap<Long, LabelKeyTO>(5);
        userEpsMap = new HashMap<Long, LabelKeyTO>(5);
    }

    public List<LabelKeyTO> getLabelKeyTOs() {
        return labelKeyTOs;
    }

    public void setLabelKeyTOs(List<LabelKeyTO> labelKeyTOs) {
        this.labelKeyTOs = labelKeyTOs;
    }

    public Map<Long, LabelKeyTO> getSowItemMap() {
        return sowItemMap;
    }

    public void setSowItemMap(Map<Long, LabelKeyTO> sowItemMap) {
        this.sowItemMap = sowItemMap;
    }

    public Map<Long, LabelKeyTO> getCostCodeItemMap() {
        return costCodeItemMap;
    }

    public void setCostCodeItemMap(Map<Long, LabelKeyTO> costCodeItemMap) {
        this.costCodeItemMap = costCodeItemMap;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

    public Map<Long, LabelKeyTO> getUserEpsMap() {
        return userEpsMap;
    }

    public void setUserEpsMap(Map<Long, LabelKeyTO> userEpsMap) {
        this.userEpsMap = userEpsMap;
    }

}
