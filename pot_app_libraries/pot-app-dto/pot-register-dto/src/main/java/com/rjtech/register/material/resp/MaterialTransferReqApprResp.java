package com.rjtech.register.material.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.material.dto.MaterialTransferReqApprTO;

public class MaterialTransferReqApprResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<MaterialTransferReqApprTO> materialTransferReqApprTOs = new ArrayList<MaterialTransferReqApprTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Map<Long, LabelKeyTO> transferMaterialMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, LabelKeyTO> storeYardMap = new HashMap<Long, LabelKeyTO>();
    private Map<Long, LabelKeyTO> userProjMap = new HashMap<Long, LabelKeyTO>();
    private boolean timeFlag = false;

    public List<MaterialTransferReqApprTO> getMaterialTransferReqApprTOs() {
        return materialTransferReqApprTOs;
    }

    public void setMaterialTransferReqApprTOs(List<MaterialTransferReqApprTO> materialTransferReqApprTOs) {
        this.materialTransferReqApprTOs = materialTransferReqApprTOs;
    }

    public Map<Long, LabelKeyTO> getTransferMaterialMap() {
        return transferMaterialMap;
    }

    public void setTransferMaterialMap(Map<Long, LabelKeyTO> transferMaterialMap) {
        this.transferMaterialMap = transferMaterialMap;
    }

    public Map<Long, LabelKeyTO> getStoreYardMap() {
        return storeYardMap;
    }

    public void setStoreYardMap(Map<Long, LabelKeyTO> storeYardMap) {
        this.storeYardMap = storeYardMap;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public boolean isTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(boolean timeFlag) {
        this.timeFlag = timeFlag;
    }
}
