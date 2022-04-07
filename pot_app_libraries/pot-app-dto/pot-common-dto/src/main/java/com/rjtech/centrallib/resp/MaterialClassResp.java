package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MaterialClassTO;
import com.rjtech.common.resp.AppResp;

public class MaterialClassResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<MaterialClassTO> materialClassTOs = new ArrayList<MaterialClassTO>();

    public List<MaterialClassTO> getMaterialClassTOs() {
        return materialClassTOs;
    }

    public void setMaterialClassTOs(List<MaterialClassTO> materialClassTOs) {
        this.materialClassTOs = materialClassTOs;
    }

}
