package com.rjtech.centrallib.resp;

import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class CentLibPlantRepairsOnLoadResp {

    private Map<Long, LabelKeyTO> classificationMap = null;

    private Map<Long, LabelKeyTO> plantServiceMap = null;

    public Map<Long, LabelKeyTO> getClassificationMap() {
        return classificationMap;
    }

    public void setClassificationMap(Map<Long, LabelKeyTO> classificationMap) {
        this.classificationMap = classificationMap;
    }

    public Map<Long, LabelKeyTO> getPlantServiceMap() {
        return plantServiceMap;
    }

    public void setPlantServiceMap(Map<Long, LabelKeyTO> plantServiceMap) {
        this.plantServiceMap = plantServiceMap;
    }

}
