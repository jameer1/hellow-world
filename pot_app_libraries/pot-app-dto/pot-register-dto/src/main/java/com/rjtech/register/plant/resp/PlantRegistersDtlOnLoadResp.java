package com.rjtech.register.plant.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.RegisterOnLoadTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.plant.dto.PlantRegisterDtlTO;

public class PlantRegistersDtlOnLoadResp extends AppResp {

    private static final long serialVersionUID = 5016462910148707833L;

    private List<PlantRegisterDtlTO> plantRegisterDtlTOs = null;
    private RegisterOnLoadTO registerOnLoadTO;

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    private Map<Long, LabelKeyTO> mesureMstrMap = null;
    private Map<Long, LabelKeyTO> userProjMap = null;

    private List<String> assertTypes = null;

    public PlantRegistersDtlOnLoadResp() {
        plantRegisterDtlTOs = new ArrayList<PlantRegisterDtlTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        mesureMstrMap = new HashMap<Long, LabelKeyTO>();
        userProjMap = new HashMap<Long, LabelKeyTO>();
        assertTypes = new ArrayList<String>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<PlantRegisterDtlTO> getPlantRegisterDtlTOs() {
        return plantRegisterDtlTOs;
    }

    public void setPlantRegisterDtlTOs(List<PlantRegisterDtlTO> plantRegisterDtlTOs) {
        this.plantRegisterDtlTOs = plantRegisterDtlTOs;
    }

    public Map<Long, LabelKeyTO> getMesureMstrMap() {
        return mesureMstrMap;
    }

    public void setMesureMstrMap(Map<Long, LabelKeyTO> mesureMstrMap) {
        this.mesureMstrMap = mesureMstrMap;
    }

    public List<String> getAssertTypes() {
        return assertTypes;
    }

    public void setAssertTypes(List<String> assertTypes) {
        this.assertTypes = assertTypes;
    }

    public RegisterOnLoadTO getRegisterOnLoadTO() {
        return registerOnLoadTO;
    }

    public void setRegisterOnLoadTO(RegisterOnLoadTO registerOnLoadTO) {
        this.registerOnLoadTO = registerOnLoadTO;
    }

}
